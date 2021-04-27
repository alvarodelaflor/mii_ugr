from django.shortcuts import render
from django.conf.urls import url
from . import views

urlpatterns = [
    url(r'^api/recetas$', views.receta_list),
    url(r'^api/recetas/(?P<pk>[0-9]+)$', views.receta_detail),
    url(r'^api/recetas/published$', views.receta_list_published)
]
