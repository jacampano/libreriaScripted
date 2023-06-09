import es.curso.CadenaConfig

def call(Closure body) {
    withEnv(["JAVA_HOME=${ tool CadenaConfig.getInstance().configuracionPipeline.java}"]) {
        body()
    }
}
