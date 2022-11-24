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
            def isStartedByUser =  stages.inicializacion()

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
        
        stages.compilacionEmpaquetado()
        //stages.desplegarArtifactory()
      
        stages.pruebasDeComponentes()
        stages.pruebasFuncionales()
        stages.vistoBuenoCalidad()
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
