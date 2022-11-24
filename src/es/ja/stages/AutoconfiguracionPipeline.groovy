package es.ja.stages

import es.ja.CadenaConfig
import es.ja.Constants

static void execute(ct) {
     ct.stage('Autoconfiguración del Pipeline') {
          def cadenaConfig = CadenaConfig.getInstance()
          def configuracionGit = cadenaConfig.configuracionGit
          def runningConfig = cadenaConfig.runningConfig
          def productType = cadenaConfig.configuracionPipeline.tipoProducto
          def tipoAccion =  ''
          def listaFWK = cadenaConfig.configuracionPipeline.frameworkPruebasAutomatizadas
          def isStartedByUser = ct.currentBuild.rawBuild.getCause(hudson.model.Cause$UserIdCause) != null

          if(isStartedByUser) {
               tipoAccion = "Ejecución manual"
          } else {
               tipoAccion = ct.env.gitlabActionType
          }

          ct.echo("--- Tipologia de producto: ${productType}")
          ct.echo("--- Tipo acción ${tipoAccion}")
          

          if (listaFWK != null && listaFWK.size() > 0 ) {

               ct.echo("--- Número de FWKs de automatización configurados: ${listaFWK.size()}")
          } else {
               ct.echo("--- No se ha especificado un FWK de pruebas. No se ejecutará las fases correspondientes a la automatización de pruebas")
               runningConfig.realizarPruebasFuncionales = false
          }
          
          

          for (framework in listaFWK) {

               ct.echo("--- Framework: ${framework.framework}")
               ct.echo("--- Version: ${framework.version}")

          }

          if (tipoAccion == 'TAG_PUSH') {
               runningConfig.esEntrega = true
          }

          switch (productType.toLowerCase()) {
               case 'maven':
                    ct.echo('--- PRODUCTO MAVEN ---')
                    runningConfig.analizarConSonar = true
                    runningConfig.analizarConOWASP = true
     
               break

               case 'mavenlib':
                    ct.echo('--- PRODUCTO LIBRERIA CON MAVEN---')
                    runningConfig.isDeployabled = false
               break

               case 'mavenpom':
                    ct.echo('--- PRODUCTO POM ---')
                    runningConfig.isDeployabled = false
                    runningConfig.analizarConSonar = false
                    runningConfig.realizarPruebasFuncionales = false
               break
               
               case 'angular':
                    ct.echo('--- PRODUCTO ANGULAR ---')
                    runningConfig.deployArtifact = false
                    runningConfig.buildImage = desplegarEn.any { item -> item in Constants.PLATAFORMAS_CONTENEDORES }
                    runningConfig.analizarConSonar = true
                    runningConfig.analizarConOWASP = true
                    runningConfig.realizarCompilacion = false
                    runningConfig.realizarPruebasUnitarias = false
                    runningConfig.generarSite = false
               break

               case 'imagen':
                    ct.echo('--- PRODUCTO IMAGEN CONTENEDOR ---')
                    runningConfig.deployArtifact = false
                    runningConfig.buildImage = desplegarEn.any { item -> item in Constants.PLATAFORMAS_CONTENEDORES }
                    runningConfig.analizarConSonar = false
                    runningConfig.analizarConOWASP = false
                    runningConfig.realizarCompilacion = false
                    runningConfig.realizarPruebasUnitarias = false
                    runningConfig.generarSite = false
                    break

               
               case 'imagenbase':
                    ct.echo('--- PRODUCTO IMAGEN BASE CONTENEDOR ---')
                    runningConfig.deployArtifact = false
                    runningConfig.buildImage = true
                    runningConfig.analizarConSonar = false
                    runningConfig.analizarConOWASP = false
                    runningConfig.realizarCompilacion = false
                    runningConfig.realizarPruebasUnitarias = false
                    runningConfig.generarSite = false
               break

               case 'test':

                    ct.echo('--- PRODUCTO TEST---')
                    runningConfig.buildImage = desplegarEn.any { item -> item in Constants.PLATAFORMAS_CONTENEDORES }
                    runningConfig.deployArtifact = false
                    runningConfig.isDeployabled = false
                    runningConfig.analizarConSonar = false
                    runningConfig.analizarConOWASP = false
                    runningConfig.realizarCompilacion = false
                    runningConfig.realizarPruebasUnitarias = false
                    runningConfig.realizarPruebasFuncionales = false
                    runningConfig.generarSite = false
                    break

                case 'car':

                    ct.echo('--- PRODUCTO CarbonApp---')
                    runningConfig.deployArtifact = true
                    runningConfig.isDeployabled = true
                    runningConfig.analizarConSonar = false
                    runningConfig.analizarConOWASP = true
                    runningConfig.realizarCompilacion = true
                    runningConfig.realizarPruebasUnitarias = false
                    runningConfig.realizarPruebasFuncionales = true
                    runningConfig.generarSite = false
               break

               default:
                    ct.echo('--- Tipo de producto no especificado. Se aborta la ejecución')
                    ct.currentBuild.result = 'ABORTED'
                    ct.error('[Error] Tipo de producto no especificado')
               break
          }
     }
}
return this
