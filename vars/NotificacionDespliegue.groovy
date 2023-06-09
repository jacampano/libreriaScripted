import es.jacampano.curso.Constants
def call (notificarTodo, estadoDespliegue, notificarUsuario, producto) {
    def entorno = null

    switch(env.STAGE_NAME){
        case Constants.FASE_DESPLIEGUE_CI :
            entorno = Constants.ENTORNO_CI
            break
        case Constants.FASE_DESPLIEGUE_TEST :
            entorno = Constants.ENTORNO_TEST
            break
        case Constants.FASE_DESPLIEGUE_FOR :
            entorno = Constants.ENTORNO_FORMACION
            break
        case Constants.FASE_DESPLIEGUE_PRE :
            entorno = Constants.ENTORNO_PREPRODUCCION
            break
        case Constants.FASE_DESPLIEGUE_PRO :
            entorno = Constants.ENTORNO_PRODUCCION
            break
        
    }

    if (notificarTodo == true) {
        if (estadoDespliegue == 'OK') {
            emailext body: "El despliegue de la aplicación en el entorno ${entorno} se ha realizado correctamente" ,
            subject: "[CALIDAD][PIPELINE][DESPLIEGUE][ENTORNO][${entorno}] Producto SW: " + producto + " ID BUILD JENKINS# ${BUILD_NUMBER}",
            to: notificarUsuario
        } else {
            emailext body: "El despliegue de la aplicación en el entorno: ${entorno} ha fallado. Por favor, revise el log de la ejecución" ,
            subject: "[CALIDAD][PIPELINE][DESPLIEGUE][ENTORNO][${entorno}][ERROR] Producto SW: " + producto + "  ID BUILD JENKINS# ${BUILD_NUMBER}",
            to: notificarUsuario
        }
    }
}
