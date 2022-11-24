import es.ja.CadenaConfig

def call(Closure body) {
    withEnv(["JAVA_HOME=${ tool CadenaConfig.getInstance().configuracionPipeline.java.versionJDK}"]) {
        body()
    }
}
