package es.jacampano.curso.stages

import es.jacampano.curso.Constants
import es.jacampano.curso.CadenaConfig

class Stages {

    def steps = null
    CadenaConfig cadenaConfig = null

    Stages(steps) {
        this.steps = steps
        this.cadenaConfig = CadenaConfig.getInstance()
    }

    boolean inicializacion() {
        return Inicializacion.execute(steps)
    }

    void autoconfiguracionPipeline() {
        return AutoconfiguracionPipeline.execute(steps)
    }

    void obtenerCodigoFuente() {
        return ObtenerCodigoFuente.execute(steps)
    }

    String obtenerInformacionProducto() {
        return ObtenerInformacionProducto.execute(steps)
    }

    void compilacionEmpaquetado() {
        return CompilacionEmpaquetado.execute(steps)
    }

    void desplegarArtifactory() {
        return DesplegarArtifactory.execute(steps)
    }

    void construirImagen() {
        return ConstruirImagen.execute(steps)
    }

    void pruebasDeComponentes() {
        steps.stage(Constants.FASE_PRUEBAS_COMPONENTES) {
                AnalisisSonar.execute(steps)
                QualityGate.execute(steps)
                AnalisisOWASP.execute(steps)
                //PruebasUnitarias.execute(steps)
        }
    }

    
    void vistoBuenoCalidad() {
        return VistoBuenoCalidad.execute(steps)
    }

    void finalizacion() {
       return Finalizacion.execute(steps)
    }

   
}
