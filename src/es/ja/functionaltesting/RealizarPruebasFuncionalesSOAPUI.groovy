package es.ja.functionaltesting
import es.ja.Constants

def call(ct) {
    def entorno = 'DEV'

    ct.withJobJava {
        def runner = '/opt/SmartBear/SoapUI-5.5.0/bin/testrunner.sh'
        entorno = entorno.toUpperCase()

        def resultado = ct.sh(script: "${runner} -r -a -f ./resultados -sTest_automatizados -cFuncionales -PENTORNO=${entorno} ./SOAP-UI/circuitoExpedientesCC_EMISOR-soapui-project.xml > salida.txt", returnStatus:true)
        ct.echo("Resultado: ${resultado}")
        ct.EnvioCorreo(Constants.FASE_PRUEBAS_FUNCIONALES) 
        if (resultado != 0 ) {
            // EnvioInformePruebasFuncionalesSOAPUI(userEmail, productName,notificarTodo,notificarA,resultado)
            ct.currentBuild.result = 'FAILED'
            ct.error('[Error] Ha fallado la verificación funcional de WS')
        }
    }
}

return this
