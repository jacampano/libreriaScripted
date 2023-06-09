package es.jacampano.curso

import groovy.json.JsonSlurperClassic
import groovy.json.JsonSlurper
import jenkins.model.Jenkins

@NonCPS
static Object jsonParse(String json) {
    return new JsonSlurperClassic().parseText(json)
}

@NonCPS
static Object cargarFichero(filename) {

    //def jsonSlurper = new JsonSlurper()
    return  new JsonSlurperClassic().parse(new File(filename))
}


return this