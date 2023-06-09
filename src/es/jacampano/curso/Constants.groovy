package es.jacampano.curso

class Constants {

    public static final String[] PLATAFORMAS_CONTENEDORES = ['Tanzu']
    public static final String[] SERVIDORES_APLICACION = ['tomcat7', 'tomcat8', 'tomcat9', 'jboss7']
    public static final String[] SERVIDORES_WSO2 = ['wso2ei', 'wso2am', 'wso2is']
    public static final String URL_SERVIDOR_CONFIGURACION = 'https://consul.chie.junta-andalucia.es/v1/kv'
    public static final String URL_GIT_API = 'https://gitlabexp.chap.junta-andalucia.es/api/v4'
    public static final String EJECUCION_MANUAL = 'Ejecución Manual'
    
    //Nombre fases
    public static final String FASE_INICIALIZACION = 'Inicialización'
    public static final String FASE_OBTENER_CONFIGURACION = 'Obtener configuración del servidor'
    public static final String FASE_AUTOCONFIGURACION = 'Autoconfiguración del Pipeline'
    public static final String FASE_DESCARGA_FUENTES = 'Descarga código fuente'
    public static final String FASE_INFORMACION_PRODUCTO = 'Obtener información producto'
    public static final String FASE_COMPILACION_Y_SUBIDA = 'Compilación y subida Artifactory'
    public static final String FASE_PRUEBAS_COMPONENTES = 'Pruebas de componentes'
    public static final String FASE_PRUEBAS_FUNCIONALES = 'Pruebas funcionales Automatizadas'
    public static final String FASE_ANALISIS_SONAR = 'Analisis con Sonar'
    public static final String FASE_QUALITY_GATE = 'Umbral de calidad'
    public static final String FASE_DEPENDENCY_CHECK = 'Analisis de dependencia'
    public static final String FASE_PRUEBAS_UNITARIAS = 'Ejecución pruebas unitarias'
    public static final String FASE_VISTO_BUENO_CALIDAD = 'Visto bueno calidad'
    public static final String FASE_PRUEBAS_ACEPTACION = 'Pruebas de aceptación'
    public static final String FASE_CONTRUCCION_IMAGEN = 'Construcción imagen'
    public static final String FASE_DESPLIEGUE_CI = 'Despliegue en CI'
    public static final String FASE_DESPLIEGUE_TEST = 'Despliegue en TEST'
    public static final String FASE_DESPLIEGUE_FOR = 'Despliegue en FORMACION'
    public static final String FASE_DESPLIEGUE_PRE = 'Despliegue en PREPRODUCCION'
    public static final String FASE_DESPLIEGUE_PRO = 'Despliegue en PRODUCCION'
    public static final String FASE_FIN = 'Fin'
    
    
    // Nombre de entornos
    public static final String ENTORNO_CI = 'CI'
    public static final String ENTORNO_TEST = 'TEST'
    public static final String ENTORNO_FORMACION = 'FOR'
    public static final String ENTORNO_PREPRODUCCION = 'PRE'
    public static final String ENTORNO_PRODUCCION = 'PRO'

    //Interrelación con usuario
    public static final String PREGUNTA_DESPLEGAR_CI = '¿Desplegar en entorno de CI?'
    public static final String PREGUNTA_DESPLEGAR_TEST = '¿Desplegar en entorno de TEST?'
    public static final String PREGUNTA_DESPLEGAR_FORMACION = '¿Desplegar en entorno de formación?'
    public static final String PREGUNTA_DESPLEGAR_PREPRODUCCION = '¿Desplegar en entorno de preproducción?'
    public static final String PREGUNTA_DESPLEGAR_PRODUCCION = '¿Desplegar en entorno de producción?'
    public static final String RESPUESTA_AFIRMATIVA = 'Si'


    //Equipos

    public static final String EQUIPO_CALIDAD = 'calidad'
    public static final String EQUIPO_NEGOCIO = 'desarrollador'
    public static final String EQUIPO_PLATAFORMA = 'produccion'

    //Repositorios

    public static final String REPOSITORIO_DESCARGA_RELEASES = 'ja-internal'
    public static final String REPOSITORIO_DESCARGA_SNAPSHOTS = 'ja-internal'
    public static final String REPOSITORIO_SUBIDA_RELEASES = 'ja-artifacts-deploy'
    public static final String REPOSITORIO_SUBIDA_SNAPSHOTS  = 'ja-ci-deploy-snapshots'
    
}
