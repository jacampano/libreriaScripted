/* groovylint-disable NoWildcardImports */
package es.ja.stages

import es.ja.Utils
import es.ja.Constants
import es.ja.CadenaConfig

static boolean execute(steps) {
     def cadenaConfig = CadenaConfig.getInstance()
     cadenaConfig.initialize(steps)
     def configuracionGit = cadenaConfig.configuracionGit
     
    
     steps.stage(Constants.FASE_INICIALIZACION) {
          steps.info()
          steps.cleanWs()

          def isStartedByUser = steps.currentBuild.rawBuild.getCause(hudson.model.Cause$UserIdCause) != null
          
          steps.echo("isStartedByUser: ${isStartedByUser}")
          
          if (isStartedByUser) {
               
               configuracionGit.tipoAccion = Constants.EJECUCION_MANUAL
               def userId = steps.currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause').userId[0]
               def userName = steps.currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause').userName
              
             
               steps.echo("Nombre usuario en Jenkins: ${userName} ")
               steps.echo ("ID usuario en Jenkins: ${userId}")
             
          }

          return isStartedByUser 

     }
}

return this
