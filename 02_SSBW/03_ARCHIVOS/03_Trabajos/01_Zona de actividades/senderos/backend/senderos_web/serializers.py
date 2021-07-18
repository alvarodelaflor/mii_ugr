from rest_framework import serializers

from .models import Visita, Comentario


class VisitaSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Visita
        owner = serializers.ReadOnlyField(source='owner.username')
        fields = ('id', 'nombre', 'descripcion', 'likes', 'foto' )


class ComentarioSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Comentario
        fields = ('visita', 'texto')


class LikesSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Visita
        fields = ('likes',)