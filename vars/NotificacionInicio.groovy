def call(producto, notificarUsuario) {
    echo 'envio correo'
    emailext body: "Se ha iniciado la cadena de integración continua para el proyecto ${producto} <br/>",
    mimeType: 'text/html',
    subject: '[CALIDAD][PIPELINE][INICIO] Producto SW: ' + producto + " ID BUILD JENKINS# ${env.BUILD_NUMBER}",
    to: "${notificarUsuario}"
    return null
}