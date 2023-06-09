package es.jacampano.curso.stages

import es.jacampano.curso.Utils
import es.jacampano.curso.Constants
import es.jacampano.curso.CadenaConfig

static void execute(ct) {
     ct.stage(Constants.FASE_DESCARGA_FUENTES) {
          def cadenaConfig = CadenaConfig.getInstance()
          def credentialsId = cadenaConfig.configuracionPipeline.credencialesGIT
          def url = 'https://github.com/jacampano/pruebasWebApp.git'
          def rama = 'main'
          ct.timeout(time: 5, unit: 'MINUTES') {

               ct.echo("--- EJECUCION WEBHOOK ---")
               //url = ct.env.gitlabSourceRepoHttpUrl
               //rama = ct.env.gitlabBranch
               ct.echo("--- Obtener Codigo Fuente desde rama:${rama}--")
               ct.checkout([$class: 'GitSCM',
                         branches: [[name: rama]],
                         doGenerateSubmoduleConfigurations: false,
                         extensions: [], gitTool: 'git', submoduleCfg: [],
                         userRemoteConfigs: [[credentialsId: credentialsId, url: url]]])
               def workspace = "${ct.env.WORKSPACE}"
          }
     }
}

return this
