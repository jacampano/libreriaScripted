package es.jacampano.curso.functionaltesting
import es.jacampano.curso.Constants

def call(ct, String options) {
    def entorno = 'DEV'
    ct.withJobMaven {
        if ( "X${options}" != 'X') {
            ct.echo('--- SE HA RELLENADO mavenGoalSelenium, por lo que se utiliza dicha cadena para lanzar las pruebas automatizadas ---')
            ct.sh("mvn ${options}")
        } else {
            ct.echo('--- NO SE HA RELLENADO mavenGoalSelenium ---')
            def cadenaSeleniumDefecto = 'mvn -f pom.xml clean verify -P' + entorno
            ct.echo("--- Se va a lanzar la verificación con Selenium Por defecto por defecto: ${cadenaCompilacionDefecto}")
            ct.sh(cadenaSeleniumDefecto)
            ct.EnvioCorreo(Constants.FASE_PRUEBAS_FUNCIONALES) 
        }
    }
}

return this
