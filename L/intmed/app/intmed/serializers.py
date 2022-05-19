from django.contrib.auth.models import User, Group
from rest_framework import serializers
from .models import Doctor, Agenda_Doc, Consultas

class DocSerializer(serializers.ModelSerializer):
    class Meta:
        model = Doctor
        fields = ('id', 'name_doctor', 'crm_doctor', 'email_doctor')

class AgendaSerializer(serializers.ModelSerializer):
    class Meta: 
        model = Agenda_Doc
        fields = ('id', 'doctor_agenda', 'dia_agenda', 'horarios_agenda')
        depth = 1

class ConsultasSerializer(serializers.ModelSerializer):
    class Meta:
        model = Consultas
        fields = ('id', 'dia', 'horario', 'data_agendamento', 'doctor_agenda')
    
class ConsultaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Consultas
        fields = ('id', 'dia', 'horario', 'data_agendamento', 'doctor_agenda')
        depth = 1 

