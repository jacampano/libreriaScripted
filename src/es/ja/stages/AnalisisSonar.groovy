package es.ja.stages

import es.ja.CadenaConfig
import es.ja.Constants

static void execute(ct) {
    def cadenaConfig = CadenaConfig.getInstance()
    ct.stageWhen('Analisis estatico de código', cadenaConfig.runningConfig.analizarConSonar) {
        def nodeJS = 'NodeJs 17.4.0'
        ct.withEnv(['SONAR_SCANNER_OPTS=-Xms512m -Xmx1024m',
        "JAVA_HOME=${ ct.tool cadenaConfig.configuracionPipeline.java}","NODEJS_HOME=${ct.tool nodeJS }"]) {
            def scannerHome = ct.tool(cadenaConfig.configuracionPipeline.sonarScaner)

            ct.catchError (buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                ct.withSonarQubeEnv(credentialsId: cadenaConfig.configuracionPipeline.sonarToken, installationName: cadenaConfig.configuracionPipeline.sonarInstancia) {
                    ct.env.NODEJS_HOME = ct.tool('NodeJs 17.4.0')
                    ct.env.NODE_PATH="/integracion_continua/jenkins2/workspace/TEST/nodeTest/node_modules/typescript/bin"
                    ct.env.PATH="${ct.env.NODEJS_HOME}/bin:${ct.env.NODE_PATH}:${ct.env.PATH}"
                    ct.sh "${scannerHome}/bin/sonar-scanner -Dproject.settings=${ct.WORKSPACE}/sonar.properties"
                }
                ct.EnvioCorreo(Constants.FASE_ANALISIS_SONAR)
            }
        }



    }
}

return this
