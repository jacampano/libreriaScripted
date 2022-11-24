package es.ja.stages

import es.ja.CadenaConfig

static void execute(ct) {
    def cadenaConfig = CadenaConfig.getInstance()
    ct.stageWhen('Umbral de Calidad', cadenaConfig.runningConfig.analizarConSonar) {
        ct.catchError (buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
            ct.sleep(10)
            ct.timeout(time: 1, unit: 'MINUTES') {
                def qg = ""
                ct.withSonarQubeEnv(credentialsId: cadenaConfig.configuracionPipeline.sonarToken, installationName: cadenaConfig.configuracionPipeline.sonarInstancia) {
                 qg = ct.waitForQualityGate()
                }
                if (qg.status != 'OK') {
                    ct.error("${qg.status}: No se ha superado el umbral requerido de calidad ")
                }
            }
        }
    }
}

return this
