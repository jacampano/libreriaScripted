package es.ja

import groovy.json.JsonSlurperClassic
import groovy.json.JsonSlurper
import jenkins.model.Jenkins
import com.michelin.cio.hudson.plugins.rolestrategy.RoleBasedAuthorizationStrategy
import com.michelin.cio.hudson.plugins.rolestrategy.Role
import com.synopsys.arc.jenkins.plugins.rolestrategy.RoleType

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