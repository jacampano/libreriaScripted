def call (notificarProduccion, entorno, producto) {
    if (entorno == 'PRE') {
        emailext body: 'El equipo de calidad ha validado el despliegue. ¿Realizar el despliegue en entorno PRE?' ,
        subject: '[CALIDAD][PIPELINE][DESPLIEGUE PRE] Producto SW: ' + producto + " ID BUILD JENKINS# ${BUILD_NUMBER}",
        to: notificarProduccion
    } else {
        emailext body: '¿Realizar despliegue en entorno PRO?' ,
        subject: '[CALIDAD][PIPELINE][DESPLIEGUE PRO] Producto SW: ' + producto + " ID BUILD JENKINS# ${BUILD_NUMBER}",
        to: notificarProduccion
    }
}
