package es.jacampano.curso.stages

import es.jacampano.curso.CadenaConfig
import es.jacampano.curso.Constants

static void execute(ct) {
    def runningConfig = CadenaConfig.getInstance().runningConfig
    ct.stageWhen(Constants.FASE_VISTO_BUENO_CALIDAD, runningConfig.esEntrega && !runningConfig.isLibrary) {
        ct.EnvioCorreo(Constants.EQUIPO_CALIDAD) // TODO
        if (ct.PreguntaContinuarDespliegue() == 'No') {
            ct.echo('--- No se ha tenido el VB de calidad. No se continuará con el despliegue en PRE ---')
            ct.currentBuild.result = 'ABORTED'
            ct.error('Se ha decidido no continuar con el despligue')
        }
    }
}

return this
