from django.contrib import admin
from django.urls import path, include
from intmed import views
from rest_framework import routers

router = routers.DefaultRouter()
router.register(r'consultas', views.ConsultaViewSet)


urlpatterns = [
    path('admin/', admin.site.urls),
    path('cadastro/', include('intmed.urls')),
    path('medicos/', views.DoctorViewSet.as_view({'get': 'list'})),
    path('agendas/', views.AgendaViewSet.as_view({'get': 'list'})),
    #path('', include(router.urls)),  
    path('consultas/', views.ConsultaViewSet.as_view({'get': 'list'}), name="consultas"),
    path('consulta/', views.ConsultaViewSet.as_view({'post': 'create'}), name="consulta"),
    
    
]
