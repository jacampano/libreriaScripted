package es.ja

class CadenaConfig {

    private static final CadenaConfig INSTANCE = new CadenaConfig()

    Map configuracionCadena = [:]
    Map configuracionPipeline = [
        java: "",
        tipoProduto: "maven",
        versionMaven: "",
        rutaPOM:"",
        goals:""
    ]
    Map configuracionGit = [:]
    Map runningConfig = [
            deployArtifact: true,
            buildImage: false,
            isDeployabled: true,
            analizarConSonar: true,
            analizarConOWASP: true,
            realizarCompilacion: true,
            realizarPruebasUnitarias: true,
            realizarPruebasFuncionales: true,
        ]
    def steps = null

    private CadenaConfig() { }

    @NonCPS
    static CadenaConfig getInstance() {
        return CadenaConfig.INSTANCE
    }

    void initialize(steps) {
        this.steps = steps
    }

    def getContext() {
        return this.steps
    }

}
