import es.ja.CadenaConfig
import es.ja.Constants

@NonCPS
def call(step){
    def configuracionGit = CadenaConfig.getInstance().configuracionGit
    def configuracionPipeline = CadenaConfig.getInstance().configuracionPipeline
    def configuracionCadena = CadenaConfig.getInstance().configuracionCadena
    def runningConfig = CadenaConfig.getInstance().runningConfig
    

    def notificarTodo = configuracionPipeline.notificaciones.notificarTodo
    def notificarUsuario = runningConfig.notificarUsuario
    def notificarCalidad = configuracionCadena.email.calidad
    def notificarProduccion = configuracionCadena.email.produccion
    def notificarAdmin = configuracionCadena.email.admin

    def entorno = configuracionGit.entorno
    def producto = CadenaConfig.getInstance().configuracionGit.producto
    def estadoDespliegue = runningConfig.estadoDespliegue

    switch(step){
        case Constants.FASE_OBTENER_CONFIGURACION :
            NotificacionInicio(producto, notificarUsuario)
            break
        case Constants.FASE_ANALISIS_SONAR :
            InformeSonar(notificarTodo, producto, notificarUsuario)
            break
        case Constants.FASE_DEPENDENCY_CHECK :
            InformeOWASP(notificarTodo, producto, notificarUsuario)
            break
        case 'Despliegue' :
            NotificacionDespliegue(notificarTodo, estadoDespliegue, notificarUsuario, producto)
            break
        case Constants.EQUIPO_CALIDAD :
            NotificacionEquipoCalidad(producto, notificarCalidad)
            break
        case Constants.FASE_PRUEBAS_FUNCIONALES :
            InformePruebasFuncionales(configuracionPipeline, notificarUsuario,producto, notificarTodo)
            break
        case Constants.EQUIPO_PLATAFORMA :
            NotificacionEquipoProduccion(notificarProduccion, entorno, producto)
            break
        case Constants.FASE_FIN:
            LogCorreo(producto, notificarUsuario)
            break
    }

}
