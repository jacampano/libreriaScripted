package es.jacampano.curso.stages

import es.jacampano.curso.CadenaConfig

static void execute(ct) {
    def cadenaConfig = CadenaConfig.getInstance()
    ct.stageWhen('Ejecución pruebas unitarias', cadenaConfig.runningConfig.realizarPruebasUnitarias) {
        ct.catchError (buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
            def configuracionJava = cadenaConfig.configuracionPipeline.java
            ct.withJobMaven {
                if ( "X${cadenaConfig.configuracionPipeline.rutaPOM}" != 'X') {
                    ct.echo('--- SE HA RELLENADO rutaPOM, por lo que se utiliza dicha ruta para lanzar el goal de test ---')
                    ct.sh("mvn -f ${cadenaConfig.configuracionPipeline.rutaPOM} test")
                } else {
                    ct.echo('--- NO SE HA RELLENADO rutaPOM. Se lanza la tarea de generación del site sobre el directorio de trabajo ---')
                    ct.sh('mvn test')
                }
            }
        }
    }
}

return this
