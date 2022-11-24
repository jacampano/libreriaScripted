

def call (producto, notificarCalidad) {
    echo '--- Enviar correo a calidad ---'
    emailext body: 'Se han ejecutado todas las tareas en entornos de validación. Por favor, valide el proyecto si quiere continuar con el despliegue en otros entornos.' ,
    subject: '[CALIDAD][PIPELINE][VALIDACION CALIDAD] Producto SW: ' + producto + " ID BUILD JENKINS# ${BUILD_NUMBER}",
    to: notificarCalidad
}
