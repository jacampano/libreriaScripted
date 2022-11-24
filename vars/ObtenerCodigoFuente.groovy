def call(String urlGIT, String rama, String credencialesGIT) {

     echo "--- Obtener Codigo Fuente desde rama:" + rama + "--"
     checkout([$class: 'GitSCM', 
                       branches: [[name: "${rama}"]], 
                       doGenerateSubmoduleConfigurations: false, 
                       extensions: [], gitTool: 'git', submoduleCfg: [], 
                       userRemoteConfigs: [[credentialsId: "${credencialesGIT}", url: "${urlGIT}"]]])
    
}

