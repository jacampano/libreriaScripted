import es.curso.CadenaConfig

def call(Closure body) {
    withJobJava {
        withMaven(maven: CadenaConfig.getInstance().configuracionPipeline.versionMaven) {
            body()
        }
    }
}
