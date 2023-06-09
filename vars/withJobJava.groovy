import es.jacampano.curso.CadenaConfig

def call(Closure body) {
    withEnv(["JAVA_HOME=${ tool CadenaConfig.getInstance().configuracionPipeline.java}"]) {
        body()
    }
}
