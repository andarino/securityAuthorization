# Generated by Django 4.0.4 on 2022-05-17 15:42

import datetime
from django.db import migrations, models
from django.utils.timezone import utc


class Migration(migrations.Migration):

    dependencies = [
        ('intmed', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='consultas',
            name='data_agendamento',
            field=models.DateTimeField(blank=True, default=datetime.datetime(2022, 5, 17, 15, 42, 50, 116019, tzinfo=utc)),
        ),
    ]
