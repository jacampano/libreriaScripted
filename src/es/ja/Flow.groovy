package es.ja

import es.ja.Constants
import es.ja.CadenaConfig
import es.ja.stages.Stages

class Flow {

    CadenaConfig cadenaConfig
    Stages stages
    def steps = null

    Flow(steps) {
        this.steps = steps
        this.cadenaConfig = CadenaConfig.getInstance()
        this.stages = new Stages(steps)
    }

    public void flow() {
        try {
            // Stages
            def (isStartedByUser) = stages.inicializacion()
            if (isStartedByUser) {
                steps.currentBuild.result = 'ABORTED'
                steps.error('[Error] Ejecucion manual iniciada desde jenkins. No se permite.')

            } else {
                normalFlow()
            }
        } catch (err) {
            steps.echo("--- Excepción Capturada ---")
            steps.echo(err.toString())
        } finally {
            steps.echo('--- SE EJECUTA SIEMPRE ---')
            //steps.EnvioCorreo(Constants.FASE_FIN)
        }
    }

    private void normalFlow() {
        steps.echo("--- Flujo normal de ejecución--- ")

        stages.autoconfiguracionPipeline()
        stages.obtenerCodigoFuente()
        stages.obtenerInformacionProducto()
        def configuracionEntornos = this.cadenaConfig.configuracionEntornos
        

        
        //stages.compilacionEmpaquetado()
        stages.desplegarArtifactory()
        
        stages.construirImagen()
        
        if(configuracionEntornos != null) {
       
            if (configuracionEntornos.keySet().contains(Constants.ENTORNO_CI)) {
                steps.echo("--- Proyecto tiene configurado entorno de CI---")
                stages.desplegarEnEntorno(Constants.ENTORNO_CI)
            } else {
                steps.echo("--- Proyecto no configurado con entorno de CI. Se omite despliegue para este entorno ---")
            }
        
        }
        stages.pruebasDeComponentes()

       

        if(configuracionEntornos != null) {

       
            if (configuracionEntornos.keySet().contains(Constants.ENTORNO_TEST)) {
                steps.echo("--- Proyecto tiene configurado entorno de TEST---")
                stages.desplegarEnEntorno(Constants.ENTORNO_TEST)
            } else {
                steps.echo("--- Proyecto no configurado con entorno de TEST. Se omite despliegue para este entorno ---")
            }


            if (configuracionEntornos.keySet().contains(Constants.ENTORNO_FORMACION)) {
                steps.echo("--- Proyecto tiene configurado entorno de FORMACION---")
                stages.desplegarEnEntorno(Constants.ENTORNO_FORMACION)
            } else {
                steps.echo("--- Proyecto no configurado con entorno de FORMACION. Se omite despliegue para este entorno ---")
            }

        }

        stages.pruebasFuncionales()
        //stages.generacionSite()

        

        
        stages.vistoBuenoCalidad()
        
        if(configuracionEntornos != null) {
            if(configuracionEntornos.keySet().contains(Constants.ENTORNO_PREPRODUCCION) && this.cadenaConfig.runningConfig.esEntrega) {

                steps.echo("--- Proyecto tiene configurado entorno de PREPRODUCCION---")
                stages.desplegarEnEntorno(Constants.ENTORNO_PREPRODUCCION)
                    
                } else {
                    steps.echo("--- Proyecto no configurado con entorno de PRODUCCION o no se trata de una entrega. Se omite despliegue para este entorno ---")
                }

         }

        stages.pruebasAceptacion()
        if(configuracionEntornos != null) {
        
            if(configuracionEntornos.keySet().contains(Constants.ENTORNO_PRODUCCION) && this.cadenaConfig.runningConfig.esEntrega) {

                steps.echo("--- Proyecto tiene configurado entorno de PRODUCCION---")
                stages.desplegarEnEntorno(Constants.ENTORNO_PRODUCCION)
                    
                } else {
                    steps.echo("--- Proyecto no configurado con entorno de PRODUCCION o no se trata de una entrega. Se omite despliegue para este entorno ---")
                }
        }
    
        stages.pruebasHumo()
        stages.finalizacion()

    
    }

    private void redespliegueFlow() {
        stages.obtenerConfiguracionServidor()
        stages.obtenerCodigoFuente()
        stages.obtenerInformacionProducto()
        stages.redesplieguePreProduccion()
        stages.redespliegueProduccion()
    }

}
