package es.jacampano.curso.stages

import es.jacampano.curso.CadenaConfig

static void execute(ct) {
    def cadenaConfig = CadenaConfig.getInstance()
    ct.stageWhen('Compilación y empaquetado', cadenaConfig.runningConfig.realizarCompilacion) {
        def shCompilacionMaven = cadenaConfig.configuracionPipeline.goals

        ct.withJobMaven {
            ct.echo ('--- Compilación y empaquetado ---')

            if ( "X${shCompilacionMaven}" != 'X') {
                ct.echo ('--- SE HA RELLENADO goals, por lo que se utiliza dicha cadena para lanzar el empaquetado ---')
                ct.sh("mvn ${shCompilacionMaven}")
            } else {
                ct.echo ('--- NO SE HA RELLENADO goals  ---')
                def cadenaEmpaquetadoDefecto = "mvn -f pom.xml clean install -DskipTests -Dmaven.test.skip=true -X -U"
                ct.echo ("--- Se va a lanzar la compilación por defecto: ${cadenaEmpaquetadoDefecto}")
                ct.sh(cadenaEmpaquetadoDefecto)
            }
        }
    }
}

return this
