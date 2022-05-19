from django.shortcuts import render
from django.http import HttpResponse
from .models import Doctor, Agenda_Doc, Consultas
from rest_framework.response import Response
from .serializers import DocSerializer, AgendaSerializer, ConsultasSerializer, ConsultaSerializer
from rest_framework import viewsets
import datetime

from rest_framework import mixins
from rest_framework.decorators import action

class DoctorViewSet(viewsets.ModelViewSet):
    queryset = Doctor.objects.all()
    serializer_class = DocSerializer

    def list(self, request, *args, **kwargs):
        doctors = Doctor.objects.all()
        serializer = DocSerializer(doctors, many=True)
        return Response(serializer.data)

class AgendaViewSet(viewsets.ModelViewSet):
    queryset = Agenda_Doc.objects.all()
    serializer_class = AgendaSerializer

    def list(self, request, *args, **kwargs):
        datasAtuais = []
        for e in Agenda_Doc.objects.all():
            if e.dia_agenda >= datetime.date.today():
                datasAtuais.append(e)
        
        agenda = datasAtuais
        serializer = AgendaSerializer(agenda, many = True)
        return Response(serializer.data)


class ConsultaViewSet(viewsets.ModelViewSet):
    queryset = Consultas.objects.all()
    serializer_class = ConsultasSerializer

    def create(self, request):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data)
    
    def list(self, request, *args, **kwargs):
        consulta = Consultas.objects.all()
        serializer = ConsultaSerializer(consulta, many = True)
        return Response(serializer.data)

