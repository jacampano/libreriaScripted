import es.ja.CadenaConfig

def call(Closure body) {
    withJobJava {
        withMaven(maven: CadenaConfig.getInstance().configuracionPipeline.versionMaven) {
            body()
        }
    }
}
