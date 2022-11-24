package es.ja.stages

import es.ja.CadenaConfig
import es.ja.Constants

static void execute(ct) {
    def cadenaConfig = CadenaConfig.getInstance()
    ct.stageWhen('Analisis estatico de código', cadenaConfig.runningConfig.analizarConSonar) {
        ct.withEnv(['SONAR_SCANNER_OPTS=-Xms512m -Xmx1024m',
        "JAVA_HOME=${ ct.tool cadenaConfig.configuracionPipeline.java}"]) {
            def scannerHome = ct.tool(cadenaConfig.configuracionPipeline.sonarScanerTool)

            ct.catchError (buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                ct.withSonarQubeEnv(credentialsId: cadenaConfig.configuracionPipeline.sonarToken, installationName: cadenaConfig.configuracionPipeline.sonarInstancia) {
                    ct.sh "${scannerHome}/bin/sonar-scanner -Dproject.settings=${ct.WORKSPACE}/sonar.properties"
                }
                //ct.EnvioCorreo(Constants.FASE_ANALISIS_SONAR)
            }
        }



    }
}

return this
