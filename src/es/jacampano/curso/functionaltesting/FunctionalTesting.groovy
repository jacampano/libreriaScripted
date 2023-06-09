package es.jacampano.curso.functionaltesting

static void orquestarPruebas(ct, frameworkPruebasAutomatizadas) {
    ct.echo("Framework Pruebas automatizadas: ${frameworkPruebasAutomatizadas}")

    for (framework in frameworkPruebasAutomatizadas) {
        ct.echo("${framework}")
        switch (framework.framework) {
            case 'Selenium':
                RealizarPruebasFuncionalesSelenium.call(ct, framework.options ?: '')
                break
            case 'SoapUI':
                RealizarPruebasFuncionalesSOAPUI.call(ct)
                break
            default:
                ct.echo('--- No se ha especificado un Framework para la ejecución de las pruebas automatizadas ---')
                ct.currentBuild.result = 'UNSTABLE'
                ct.error('Framework no indicado/soportado')
                break
        }
    }
}

return this
