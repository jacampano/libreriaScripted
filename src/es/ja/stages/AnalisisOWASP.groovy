package es.ja.stages
import es.ja.CadenaConfig
import es.ja.Utils
import es.ja.Constants

static void execute(ct) {
    def cadenaConfig = CadenaConfig.getInstance()
    def data = [:]
    def filename = ct.env.WORKSPACE+"/dependency-check-report.json"
    def arguments = " --format ALL "

    ct.stageWhen('Analisis de dependencias', cadenaConfig.runningConfig.analizarConOWASP) {

      
        ct.catchError (buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
            ct.dependencyCheck additionalArguments: arguments, odcInstallation: cadenaConfig.configuracionPipeline.dependencyCheckTool
         }
        
        if (ct.fileExists(filename)) {
            data = Utils.cargarFichero(filename)
        } else {
            ct.currentBuild.result = "UNSTABLE"
            ct.error('[Advertencia] No ha sido posible obtener el fichero de análisis.')
            
        }
    
        //ct.EnvioCorreo(Constants.FASE_DEPENDENCY_CHECK) 
    
    }
}


return this
