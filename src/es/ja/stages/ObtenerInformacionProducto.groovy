package es.ja.stages

import es.ja.CadenaConfig

static String execute(ct) {
    ct.stage('Obtener información producto') {
        def cadenaConfig = CadenaConfig.getInstance()
        def runningConfig = cadenaConfig.runningConfig

        ct.echo("Tipo producto:"+cadenaConfig.configuracionPipeline.tipoProducto.toLowerCase())        
           
        switch(cadenaConfig.configuracionPipeline.tipoProducto.toLowerCase()) {

            case ~/^maven.*/ : 
                def mavenPom = ct.readMavenPom(file: cadenaConfig.configuracionPipeline.rutaPOM)
                runningConfig.groupId = mavenPom.getGroupId()
                runningConfig.artifactId = mavenPom.getArtifactId()
                runningConfig.version = mavenPom.getVersion()
    
                ct.echo("--- VERSION: ${runningConfig.version}")
                runningConfig.type = mavenPom.getPackaging()

                if (runningConfig.esEntrega) {
                    if(runningConfig.version.toUpperCase().contains("SNAPSHOT")) {
                        ct.currentBuild.result = 'ABORTED'
                        ct.error('[Error] En ENTREGAS no se permite subidas de artefactos con versión SNAPSHOT. Por favor, revisa el pom y actualiza la versión')

                    } else {
                        runningConfig.etiqueta = runningConfig.version
                    }
                    
                } else {
                    if(!runningConfig.version.toUpperCase().contains("SNAPSHOT")) {
                        ct.currentBuild.result = 'ABORTED'
                        ct.error('[Error] Durante el desarrollo, los artefactos generados deben utilizar la notación SNAPSHOT en la versión. Por favor, revisa el pom y actualiza la versión')

                    } else {
                        runningConfig.etiqueta = runningConfig.version.concat('-').concat(ct.env.BUILD_ID)

                    }
                    
                }
                return runningConfig.etiqueta
            break

            case 'car':

                def mavenPom = ct.readMavenPom(file: cadenaConfig.configuracionPipeline.java.rutaPOM)
                runningConfig.groupId = mavenPom.getGroupId()
                runningConfig.artifactId = mavenPom.getArtifactId()
                runningConfig.version = mavenPom.getVersion()
                runningConfig.type = mavenPom.getPackaging()

                if (runningConfig.esEntrega) {
             
                        runningConfig.etiqueta = runningConfig.version
                    
                } else {
               
                        runningConfig.etiqueta = runningConfig.version.concat('-').concat(ct.env.BUILD_ID)

                }
                return runningConfig.etiqueta


            break

            case 'angular':

                def filename = ct.env.WORKSPACE+"/package.json"
                def packageJSON = ct.readJSON(file: filename)

                runningConfig.artifactId = packageJSON.name
                runningConfig.version = packageJSON.version
                runningConfig.etiqueta = runningConfig.version.concat('-').concat(ct.env.BUILD_ID)
                

            break

            case 'imagen':
                ct.echo('--- PRODUCTO IMAGEN CONTENEDOR ---')
                runningConfig.etiqueta = ct.input(message: 'Indique versión para etiquetado de la imagen a construir',
                         parameters: [ct.string(defaultValue: '',
                         description: '', name: 'Version')])
                return runningConfig.etiqueta
            break

            case 'imagenbase':

                ct.echo('--- PRODUCTO IMAGEN BASE ---')
                ct.echo('--- PRODUCTO IMAGEN CONTENEDOR ---')
                runningConfig.etiqueta = ct.input(message: 'Indique versión para etiquetado de la imagen a construir',
                         parameters: [ct.string(defaultValue: '',
                         description: '', name: 'Version')])
                return runningConfig.etiqueta


            break

            default :
                ct.echo('Caso por defecto') 

            break



        }
        
    }
}

return this
