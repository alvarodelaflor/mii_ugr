from django.shortcuts import render

from django.http.response import JsonResponse
from rest_framework.parsers import JSONParser
from rest_framework import status

from .models import Receta
from .serializers import RecetaSerializer
from rest_framework.decorators import api_view


@api_view(['GET', 'POST', 'DELETE'])
def receta_list(request):
    if request.method == 'GET':
        tutorials = Receta.objects.all()

        title = request.GET.get('title', None)
        if title is not None:
            tutorials = tutorials.filter(title__icontains=title)

        tutorials_serializer = RecetaSerializer(tutorials, many=True)
        return JsonResponse(tutorials_serializer.data, safe=False)


@api_view(['GET', 'PUT', 'DELETE'])
def receta_detail(request, pk):
    try:
        receta = Receta.objects.get(pk=pk)
    except receta.DoesNotExist:
        return JsonResponse({'message': 'The tutorial does not exist'}, status=status.HTTP_404_NOT_FOUND)


@api_view(['GET'])
def receta_list_published(request):
    tutorials = Receta.objects.filter(published=True)

    if request.method == 'GET':
        tutorials_serializer = RecetaSerializer(tutorials, many=True)
        return JsonResponse(tutorials_serializer.data, safe=False)
