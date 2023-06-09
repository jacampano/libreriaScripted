package es.jacampano.curso.mails
def call (producto, notificarUsuario) {
    echo 'Envio correo de log'
    emailext attachLog: true, attachmentsPattern: 'generatedFile.log',
        compressLog: true,
        body: "La ejecución ha finalizado con resultado: ${currentBuild.result} <br/>" +
        "Puede consultar información adicional sobre la ejecucion en el siguiente enlace <a href=\"${JOB_URL}\">Panel tarea</a>   </br>" +
        "También puede consultat el site generado en <a href=\"${JOB_URL}Site_20Report/\">Site Report</a> </br> </br> </br>" +
        '----- A continuación se muestra las últimas 150 lineas del log -----' + '</br> </br>' +
        '----- También puede consultar el log completo en el zip adjunto -----' +
        '${BUILD_LOG, maxLines=150, escapeHtml=false}',
        mimeType: 'text/html',
        subject: '[CALIDAD][PIPELINE][GENERAL] Producto SW: ' + producto + " ID BUILD JENKINS# ${BUILD_NUMBER} ${currentBuild.result}",
        to: notificarUsuario
}
