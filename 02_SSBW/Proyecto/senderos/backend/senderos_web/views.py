from django.contrib import messages
from django.http import HttpResponse
from django.shortcuts import render, redirect
from django.template import loader
from senderos_web.models import Visita, Comentario, VisitaForm


def index(request):
    visitasCompletas = Visita.objects.order_by('nombre')
    template = loader.get_template("senderos_web/index.html")
    context = {
        'visitasCompletas': visitasCompletas,
        #'lista_visitas': lista_visitas,
        #'num_comentarios': num_comentarios,
        #'num_visitas' : num_visitas,
    }
    return HttpResponse(template.render(context, request) )


def add_visita(request):
    visitasCompletas = Visita.objects.order_by('nombre')
#    num_comentarios = Comentario.objects.all().count()
#    num_visitas = Visita.objects.all().count()
    form = VisitaForm()
    if request.method == 'POST':  # de vuelta con los datos
        form = VisitaForm(request.POST, request.FILES)  # bound the form
        if form.is_valid():
            post = form.save(commit=False)
#            post.owner = request.user
            post.save()
            msg = "Nueva visita creada correctamente"
            messages.success(request, msg)
            return redirect('index')
    template = loader.get_template('senderos_web/add_visit.html')
    context = {
        'form': form,
        'num_comentarios': 2,
        'num_visitas': 3,
        'visitasCompletas': visitasCompletas,
    }
    return HttpResponse(template.render(context, request))