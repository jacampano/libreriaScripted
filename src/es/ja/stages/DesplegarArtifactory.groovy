package es.ja.stages

import es.ja.CadenaConfig
import es.ja.Constants

static void execute(ct) {
    def cadenaConfig = CadenaConfig.getInstance()
    ct.stageWhen(Constants.FASE_COMPILACION_Y_SUBIDA, cadenaConfig.runningConfig.deployArtifact) {
        def configuracionCadena = cadenaConfig.configuracionCadena

        switch(cadenaConfig.configuracionPipeline.tipoProducto) {

            case ~/^maven.*/ :

                    ct.withJobJava {
                        ct.withMaven (maven: cadenaConfig.configuracionPipeline.versionMaven) {
                            def servidorArtifactory = ct.Artifactory.server(configuracionCadena.servidorArtifactory)
                            servidorArtifactory.bypassProxy = true
                            servidorArtifactory.credentialsId = configuracionCadena.credencialesArtifactory

                            def rtMaven = ct.Artifactory.newMavenBuild()
                            
                        
                            rtMaven.tool = cadenaConfig.configuracionPipeline.versionMaven
                            rtMaven.resolver server: servidorArtifactory, releaseRepo: Constants.REPOSITORIO_DESCARGA_RELEASES, snapshotRepo: Constants.REPOSITORIO_DESCARGA_SNAPSHOTS
                            rtMaven.deployer server: servidorArtifactory, releaseRepo: Constants.REPOSITORIO_SUBIDA_RELEASES, snapshotRepo: Constants.REPOSITORIO_SUBIDA_SNAPSHOTS

                            
                                try {
                                    def buildInfo = rtMaven.run pom: cadenaConfig.configuracionPipeline.rutaPOM, goals: cadenaConfig.configuracionPipeline.goals
                                    buildInfo.env.capture = true
                                    buildInfo.env.collect()
                                    servidorArtifactory.publishBuildInfo buildInfo

                            } catch (Exception err) {
                                    ct.echo('--- ERROR SUBIDA ARTIFACTORY ---')
                                    if (err != null) {
                                        ct.echo(err)
                                    }
                                    ct.currentBuild.result = 'ABORTED'
                                    ct.error('[Error] Se ha producido un error en la subida a Artifactory')
                                }
                            }   
                    }
            
            break

            case 'otro':
                ct.echo('--Otras acciones --')
            break


            default:

                ct.echo('--- ACCION POR DEFECTO POR DEFINIR')

            break

        }


    }
}

return this