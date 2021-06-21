from django.shortcuts import render

def index(request):
    return render(request, "senderos_web/index.html")