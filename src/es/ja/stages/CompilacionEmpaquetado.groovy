package es.ja.stages

import es.ja.CadenaConfig

static void execute(ct) {
    def cadenaConfig = CadenaConfig.getInstance()
    ct.stageWhen('Compilación y empaquetado', cadenaConfig.runningConfig.realizarCompilacion) {
        def shCompilacionMaven = cadenaConfig.configuracionPipeline.java.shCompilacionMaven

        ct.withJobMaven {
            ct.echo ('--- EMPAQUETADO ---')

            if ( "X${shCompilacionMaven}" != 'X') {
                ct.echo ('--- SE HA RELLENADO shCompilacionMaven, por lo que se utiliza dicha cadena para lanzar el empaquetado ---')
                ct.sh("mvn ${shCompilacionMaven}")
            } else {
                ct.echo ('--- NO SE HA RELLENADO mavenGoalCompile ---')
                def cadenaEmpaquetadoDefecto = "mvn -f pom.xml clean install -DskipTests -Dmaven.test.skip=true -X -U -P${cadenaConfig.configuracionGit.entorno}"
                ct.echo ("--- Se va a lanzar la compilación por defecto: ${cadenaEmpaquetadoDefecto}")
                ct.sh(cadenaEmpaquetadoDefecto)
            }
        }
    }
}

return this
