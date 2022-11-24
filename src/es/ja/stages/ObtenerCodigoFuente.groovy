package es.ja.stages

import es.ja.Utils
import es.ja.Constants
import es.ja.CadenaConfig

static void execute(ct) {
     ct.stage(Constants.FASE_DESCARGA_FUENTES) {
          def cadenaConfig = CadenaConfig.getInstance()
          def credentialsId = cadenaConfig.configuracionCadena.credencialesGIT
          def url = ''
          def rama = ''
          ct.timeout(time: 5, unit: 'MINUTES') {

               ct.echo("--- EJECUCION WEBHOOK ---")
               url = cadenaConfig.configuracionGit.url
               rama = cadenaConfig.configuracionGit.rama
               ct.echo("--- Obtener Codigo Fuente desde rama:${rama}--")
               /*ct.checkout([$class: 'GitSCM',
                         branches: [[name: rama]],
                         doGenerateSubmoduleConfigurations: false,
                         extensions: [], gitTool: 'git', submoduleCfg: [],
                         userRemoteConfigs: [[credentialsId: credentialsId, url: url]]])*/
               def workspace = "${ct.env.WORKSPACE}"
               ct.DescargarRepositorio(rama,url,credentialsId,workspace,ct)
          }
     }
}

return this
