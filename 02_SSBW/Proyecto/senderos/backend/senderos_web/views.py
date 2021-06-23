# Create your views here.

from django.shortcuts import render, redirect
from django.http import HttpResponseRedirect, HttpResponse, JsonResponse
from senderos_web.models import Visita, Comentario, VisitaForm
from django.template import loader
from django.contrib import messages
from django.contrib.auth.decorators import login_required, permission_required
from django.contrib.admin.views.decorators import staff_member_required
from rest_framework import viewsets, permissions
from senderos_web.serializers import ComentarioSerializer, VisitaSerializer, LikesSerializer
from rest_framework.parsers import JSONParser
#from senderos_web.permisions import IsOwnerOrReadOnly
from django.views.decorators.csrf import csrf_exempt
#from decouple import config
import os
import requests
import json
import logging
import datetime

logger = logging.getLogger(__name__)


# Leave the rest of the views (detail, results, vote) unchanged
def index(request):
    lista_visitas = Visita.objects.order_by('-likes')[:6]
    visitasCompletas = Visita.objects.order_by('nombre')
    num_comentarios = Comentario.objects.all().count()
    num_visitas = Visita.objects.all().count()
    template = loader.get_template('senderos_web/index.html')
    context = {
        'visitasCompletas': visitasCompletas,
        'lista_visitas': lista_visitas,
        'num_comentarios': num_comentarios,
        'num_visitas': num_visitas,
    }
    return HttpResponse(template.render(context, request))


def detalle_visita(request, visita_id):
    visitasCompletas = Visita.objects.order_by('nombre')
    num_comentarios = Comentario.objects.all().count()
    num_visitas = Visita.objects.all().count()
    try:
        mi_visita = Visita.objects.get(id=visita_id)
    except Visita.DoesNotExist:
        msg = "Error al obtener el detalle de una visita: La visita con id " + visita_id + " no existe."
        logger.error(msg)
        return HttpResponse(status=404)
    comentarios = Comentario.objects.filter(visita=mi_visita)
    lugar_buscar = mi_visita.nombre.replace(" ", "+") + "+Granada"
    ubi = 'https://nominatim.openstreetmap.org/search?q={}&format=json'.format(lugar_buscar)
    result = requests.get(ubi)
    logger.warning(result.text)
    data = json.loads(result.text)
    if data:
        lat = data[0]['lat']
        lon = data[0]['lon']
    else:
        logger.warning("Visita no ubicada, ubicamos la visita en Granada")
        lat = '37.183054'
        lon = '-3.6021928'
    template = loader.get_template('senderos_web/show_visit.html')
    context = {
        'visitasCompletas': visitasCompletas,
        'mi_visita': mi_visita,
        'comentarios': comentarios,
        'num_comentarios': num_comentarios,
        'num_visitas': num_visitas,
        'lat': lat,
        'lon': lon,
    }
    return HttpResponse(template.render(context, request))


#@login_required
#@staff_member_required
def add_visita(request):
    visitasCompletas = Visita.objects.order_by('nombre')
    num_comentarios = Comentario.objects.all().count()
    num_visitas = Visita.objects.all().count()
    form = VisitaForm()
    if request.method == 'POST':  # de vuelta con los datos
        form = VisitaForm(request.POST, request.FILES)  # bound the form
        if form.is_valid():
            post = form.save()
            post.owner = request.user
            post.save()
            msg = "Nueva visita creada correctamente"
            messages.success(request, msg)
            return redirect('index')
    template = loader.get_template('senderos_web/add_visit.html')
    context = {
        'form': form,
        'num_comentarios': num_comentarios,
        'num_visitas': num_visitas,
        'visitasCompletas': visitasCompletas,
    }
    return HttpResponse(template.render(context, request))


#@login_required
#@staff_member_required
def edit_visita(request, visita_id):
    visitasCompletas = Visita.objects.order_by('nombre')
    num_comentarios = Comentario.objects.all().count()
    num_visitas = Visita.objects.all().count()
    try:
        visit = Visita.objects.get(pk=visita_id)
        name = visit.nombre
    except Visita.DoesNotExist:
        msg = "Error al editar una visita: La visita con id " + visita_id + " no existe."
        logger.error(msg)
        return HttpResponse(status=404)
    form = VisitaForm(instance=visit)
    if request.method == 'POST':  # de vuelta con los datos
        form = VisitaForm(request.POST, request.FILES,
                          initial=visit.__dict__, instance=visit)  # bound the form
        if form.is_valid():
            if form.has_changed():
                form.save()
            msg = "Visita " + name + " ha sido modificada correctamente"
            messages.success(request, msg)
            return redirect('index')
    template = loader.get_template('senderos_web/edit_visita.html')
    context = {
        'form': form,
        'visit': visit,
        'num_comentarios': num_comentarios,
        'num_visitas': num_visitas,
        'visitasCompletas': visitasCompletas,
    }
    return HttpResponse(template.render(context, request))


#@login_required
#@staff_member_required
def delete_visita(request, visita_id):
    visit = Visita.objects.get(pk=visita_id)
    name = visit.nombre
    visit.delete()
    msg = "Visita " + name + " ha sido eliminada correctamente"
    messages.success(request, msg)
    return redirect('index')


class VisitaViewSet(viewsets.ModelViewSet):
    queryset = Visita.objects.all().order_by('id')
    serializer_class = VisitaSerializer
#    permission_classes = [
#        permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly]

    def perform_create(self, serializer):
        serializer.save(owner=self.request.user)


class ComentarioViewSet(viewsets.ModelViewSet):
    queryset = Comentario.objects.all().order_by('id')
    serializer_class = ComentarioSerializer
#    permission_classes = [
#        permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly]


@csrf_exempt
def get_likes(request, visita_id):
    try:
        visita = Visita.objects.get(id=visita_id)
    except Visita.DoesNotExist:
        msg = "Error al obtener los likes: La visita con id " + visita_id + " no existe."
        logger.error(msg)
        return HttpResponse(status=404)
    if request.method == 'GET':
        serializer = LikesSerializer(visita)
        return JsonResponse(serializer.data)
    elif request.method == 'PUT':
        data = JSONParser().parse(request)
        serializer = LikesSerializer(visita, data=data)
        if serializer.is_valid():
            serializer.save()
            msg = "Likes de la visita " + visita.nombre + ' actualizado.'
            return JsonResponse(serializer.data)
        return JsonResponse(serializer.errors, status=400)