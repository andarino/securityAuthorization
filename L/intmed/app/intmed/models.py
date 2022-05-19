from django.db import models
from django.db.models import CharField, Model 
import datetime
from django.core.exceptions import ValidationError
from django.utils import timezone

def verificarDia(self):
    if self < datetime.date.today():
        raise ValidationError(
            'O dia e anterior a hoje'
        )

def checkMedicoDisp(dia_agenda):
    for e in Agenda_Doc.objects.all():
        if dia_agenda == e.dia_agenda:
            raise ValidationError('dia ja agendado para o doutor')

def verificarDiaConsulta(dia):
    for e in Consultas.objects.all():
        if dia == e.dia:
            raise ValidationError('dia consulta ja marcado')

def checkHorario(horario):
    if (horario in Agenda_Doc.horarios_agenda):
        return True
    else:
        raise ValidationError(
            'horario ou dia nao disponivel'
        )

def checkDoctorAgendaId(v):
    if v == None:
        return 'doctor_agenda id faltando'

class Doctor(models.Model):
    name_doctor = models.CharField(max_length=200)
    crm_doctor = models.IntegerField(default=0, unique = True)
    email_doctor = models.EmailField(max_length=200, blank= True)

    def __str__(self):
        return self.name_doctor

class Agenda_Doc(models.Model):
    doctor_agenda = models.ForeignKey(Doctor, on_delete=models.CASCADE)
    dia_agenda = models.DateField('data alocacao', validators=[verificarDia, checkMedicoDisp])
    horarios_agenda =  ['14:00', '11:40', '15:30']

class Consultas(models.Model):
    horario= models.CharField(max_length=12, validators=[checkHorario])
    dia = models.DateField('data alocacao', unique = True, blank=False, validators=[verificarDiaConsulta])
    data_agendamento= models.DateTimeField(blank= True, default=timezone.now())
    doctor_agenda = models.ForeignKey(Doctor, on_delete=models.CASCADE, validators=[checkDoctorAgendaId])

