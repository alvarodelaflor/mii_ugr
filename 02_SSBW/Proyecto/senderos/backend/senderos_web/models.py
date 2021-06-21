# -*- coding: utf-8 -*-
from django.db import models

from sorl.thumbnail import ImageField
from django import forms
from django.core.exceptions import ValidationError
from django.utils.translation import gettext_lazy as _
from django.core.validators import RegexValidator, FileExtensionValidator
from django.contrib.auth import get_user_model


def validate_capitalized(value):
    if value != value.capitalize():
        raise ValidationError(_("La primera letra de la descripción debe ser mayuscula! Ej: %(value)s"),
                              code='invalid',
                              params={'value': value.capitalize()})


# Create your models here.
class Visita(models.Model):
    nombre = models.CharField(max_length=155, unique=True)
    descripcion = models.CharField(
        max_length=1000, validators=[validate_capitalized])
    likes = models.IntegerField(default=0)
    foto = ImageField(upload_to='fotos', blank=True,
                      validators=[FileExtensionValidator(allowed_extensions=['jpg', 'png'])])
    owner = models.ForeignKey('auth.User', related_name='visita',
                              on_delete=models.CASCADE)

    def save(self, *args, **kwargs):
        super(Visita, self).save(*args, **kwargs)

    def __str__(self):  # For Python 2, use __str__ on Python 3
        return self.nombre


class Comentario(models.Model):
    visita = models.ForeignKey(Visita, on_delete=models.CASCADE)
    texto = models.CharField(max_length=500)

    def save(self, *args, **kwargs):
        super(Comentario, self).save(*args, **kwargs)

    def __str__(self):  # For Python 2, use __str__ on Python 3
        return self.texto


class VisitaForm(forms.ModelForm):
    class Meta:
        model = Visita
        fields = ['nombre', 'descripcion', 'foto']
        widgets = {
            'nombre': forms.TextInput(attrs={'size': 40}),
            'descripcion': forms.Textarea(attrs={'rows': 3, 'cols': 50}),
            'foto': forms.FileInput()
        }