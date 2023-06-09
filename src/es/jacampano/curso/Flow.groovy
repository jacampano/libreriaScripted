package es.jacampano.curso

import es.jacampano.curso.Constants
import es.jacampano.curso.CadenaConfig
import es.jacampano.curso.stages.Stages

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
                steps.echo("--- Ejecución manual del flujo ---")
                normalFlow()
                // Una ejecución manual se podría abortar de la siguiente manera:
                //steps.currentBuild.result = 'ABORTED'
                //steps.error('[Error] Ejecucion manual iniciada desde jenkins. No se permite.')

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
        stages.AnalisisOWASP()
        stages.vistoBuenoCalidad()
        stages.finalizacion()
    
    }
   
}
