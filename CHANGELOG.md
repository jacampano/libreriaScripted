# Changelog
## [0.8.0] - (14/07/2022)

* Inclusión verificación imágenes base servicio de producción
* Se corrigen las llamadas realizadas en la fase pruebas funcionales ya que estas estan no estaban funcionando
* Tras la refactorizacion realizada en la version 0.6.0, algunos token se utilizaban directamente en el codigo. Se ha corregido
* Mejora en las notificaciones
* Mejora en la construccion de imagenes docker

### Añadido

* Se ha añadido la opcion "no-cache" a la hora de construir una imagen Docker
* Añadido centralizador de notificaciones:
    - EnvioCorreo.groovy
* Se ha añadido una verificacion para saber que los ficheros Dockerfile contienen la url del repositorio de imagenes correctamente
* Añadido metodo para descarga de repositorios:
    - DescargarRepositorio.groovy

### Modificado

* Modificado nombre del stage "Pruebas funcionales" a "Pruebas funcionales Automatizadas"
* Modificado la forma de obtener el codigo fuente ya no se utiliza el plugin y se usa el metodo DescargarRepositorio. Los archivos afectados son:
    - ObtenerCodigoFuente.groovy
    - AnalisisOWASP.groovy
    - DesplegarWSO2.groovy
* Modificado el envio de correos ahora se utilizada el metodo EnvioCorreo

### Obsoleto

- No aplica


## [0.7.0] - (30/11/2021)

* En versión 0.6.0, se hizo una refactorización para pasar de un piopeline declarativo a un scripted pipeline. Algunas llamadas dejaron de funcionar correctamente. Se han corregido.
* Se realizan configuraciones (y las implementaciones necesariaa) adicionales para soportar las nuevas funcionalidades de disparo manual.
* Se realizan ajustes para que los disparadores manuales funcionen correctamente
* Se realizan modificaciones en la forma de obtener el código de Gitlab para contemplar la posibilidad de los disparos manuales.
* Se arreglan los despliegues (redespliegues sigue sin funcionar)
* Se alinean los entornos de despliegues con el documento . Con esta versión se podría desplegar en CI/TEST/FOR/PXP/EXP
* Se elimina la necesidad de indicar el war a desplegar (se descarga todo de artifactory y se despliega todos los wars)
* Se posibilita despliegues de varios tipos en un mismo job mediante configuración.
* Se hacen ajustes para la subida a Artifactory
* Se realizan mejoras en las notificaciones. Se intentará notificar al autor del cambio, en vez de a la lista. Si no fuera posible, a la lista.
* Mejora notificaciones
* Mejora en tarea de limpieza para borrar imagenes locales generadas



### Añadido

- Integración flujo normal / redespliegues en el mismo pipeline (redespliegues no funcional)
- Posibilidad de despliegue en CI/TEST/FOR/PXP/EXP
- Inclusión tipologia producto "Imagen" para poder generar y desplegar imagenes de proyectos no java


### Modificado

- Refactorización mayor. Se pasa de declarative pipeline a scripted pipeline, para tener mayor flexibilidad y control sobre el flujo.

### Obsoleto

- No aplica


### Eliminado 

- Se elimina la fase "Generar site", ya que los proyectos no consultan la información generada en fase y consumo tiempo el realizarla.
- Se elimina la fase "Compilación y empaquetado" y se agrupa en una "Comñppilación y subida a Artifactory"

## [0.5.0] - (13/04/2021)

* Acciones manuales en función del rol. En las fases de parada, se ha establecido el rol o grupo que debe realizar la acción. Solo una persona que tenga asignado el rol o grupo correspondiente, podrá avanzar o parar la cadena.
* Implementación inicial QualityGates. Se realiza una implementación inicial que lanza la ejecución de QualityGate del proyecto. De momento, la no superación del umbral no supondrá una parada en la cadena.
* Se elimina la utilización del plugin withKubeConfig ya que se utilizan los ficheros kubeconfig que producción ha subido a Jenkins.
* Se añade posibilidad de realizar despliegue tanto en contenedores como en virtual para un mismo proyecto. De momento, se ha optado por utilizar el literal "ambos" en la propiedad "desplegarEn" que indicará que se realizará despliegue simultaneo en máquinas virtuales y contenedores, pero en un futuro, se pasará un array de opciones para desplegar (no limitandose las opciones)
* Se crea una cadena diferente para los redespligues. Esta cadena se utilizará únicamente para volver a despelgar en un entorno de preproducción o producción un producto que previamente se haya etiquetado (generado entrega)
* Se mejora la integración con los FWKs de automatización (FWKE2E y FWKAPIs)
* Refactorización del código para mejorar la legibilidad y otras tareas de refactorización.

### Añadido

- Cadena de Redespliegues
    - BuscarProyectoEnGIT.groovy
    - ObtenerConfiguracionCadenaUnica.groovy
    - ObtenerURLRepositorio.groovy
    - SelectorConfirmacionProducto.groovy
    - SelectorEntorno.groovy
    - SelectorPlataforma.groovy
    - SelectorVersion.groovy
- Acciones manuales en función del rol/grupo de usuario
    - PreguntaContinuarDespliegue.groovy
- Implementación inicial QualityGates
    - QualityGate.groovy

### Modificado

- Eliminación plugin withKubeConfig para despliegue en cluster K8s
    - DesplegarImagenEnClusterKubernetes.groovy
- Modificado ejecución FWKs automatizados de pruebas (FWKE2E y FWKAPI)
    - RealizarPruebasFWKAPIs.groovy
    - RealizarPruebasFWKE2E.groovy
- Opción para despliegue simultáneo en máquinas virtual y contenedores
    - DesplegarContenedor.groovy
    - DescargarArtifactory.groovy
    

### Obsoleto

No aplica

### Eliminado

No aplica

## [0.4.0] - (13/11/2020)


* Consolidación de la configuración a través del servidor Consul. Se ha realizado un repaso a toda la parametrización utilizada por la cadena y siempre que ha sido  posible, se ha  externalizado dicha configuración. 
Es posible que se tenga que cambiar la estructura actual en la que se guarda la configuración en Consul, por lo que habrá que realizar también adaptaciones sobre la configuración existente y en los scripts que automatizan la creación del proyecto en Consul.
* Consolidación de la automatización de las pruebas funcionales. Se implementa los cambios necesarios para la ejecución del FWKAPIs realizado por equipo Calidad
* Consolidación y orden de las fases: Se ha dado un repaso a las fases existentes y el orden en la que se ejecutan para minimizar el tiempo de ejecución y las posibles dependencias de unas con otras. Por ejemplo, se ha agrupado en úna única fase la compilación y empaquetado. 
* Despliegue en contenedores: Se ha implementado la lógica necesaria para el despliegue en contenedores. La plataforma de contenedores para está versión es Tanzu. Se hace uso del artefacto inmutable y de las imágenes base que proporcionar el servicio de producción para construir la imagen necesaria para el despliegue. Como registro/repositorio de imágenes, se utiliza Harbor. 
* Cambios para utilización artefactos inmutables y desechar flujo Gitlab Flow: Se va a realizar un cambio en el enfoque de como se está realizando el proceso de integración continua. Vamos a dar de lado a Gitlab Flow (donde se tendría la posibilidad de crear artefactos por cada uno de los entornos a desplegar, incluso cabe la posibilidad de que los artefactos tuviesen distinta funcionalidad implementada), por la aproximación de artefacto inmutable, es decir, crearíamos un único artefacto en la primera construcción y ese es el que se promocionaría al resto de entornos (sin realizar ningún tipo de modificación adicional o compilación)

### Añadido

- Consolidación de la configuración a través del servidor Consul
    - ObtenerConfiguracionPipeline.groovy
    - ObtenerConfiguracionEntorno.groovy
- Pruebas funcionales con FWKAPIs
    - RealizarPruebasFWKAPIs.groovy
- Despliegue en contenedores
    - ConstruirImagenDocker.groovy
    - ConstruirImagenOKD.groovy
    - DesplegarImagenEnClusterKubernetes.groovy
    - DesplegarContenedor.groovy
- Consolicación y orden de fases
    - PreguntaContinuarDespliegue.groovy
    - EnvioNotificacionEquipoCalidad.groovy
    - EnvioNotificacionEquipoProduccion.groovy


### Modificado

Correciones de errores y ajustes sobre

- ObtenerCodigoFuente.groovy
- EnvioInformePruebasFuncionales.groovy Organización e inclusión notificaciones FWKAPIs

### Obsoleto

Las siguientes funciones son obsoletas, por lo que se eliminarán en próximas versiones

- ObtenerConfiguracion.groovy

### Eliminado

No aplica

## [0.3.0] - (15/06/2020)


En esta versión se ha implementado, en la fase correspondiente, las pruebas automatizadas tras el despliegue en entorno de pruebas. En concreto:

* Pruebas automatizadas con SOAP-UI para los proyectos que generen WS
* Pruebas funcionales automatizadas con el framework seleccionado a tal efecto.

Se avanza en el alineamiento de la cadena con las convenciones y normativas desarrolladas en:

[Normas y convenciones desarrollo para uso circuito de integración continua](https://teo.i-administracion.junta-andalucia.es/projects/ccc/wiki/Normas_y_convenciones_desarrollo_para_uso_circuito_de_integración_continua)

Para realizar la configuración del proyecto, se ha implementado la interación con un servidor que sirva como repositorio de configuración y no el fichero tuberia.json.


### Añadido

- Integración con Consul para el almacenaje de los pares claves/valor de configuración.
    - ObtenerConfiguracion.groovy
- Implementación pruebas funcionales WS vía SOAP-UI
- Implementación pruebas funcionales automatizadas con Selenium
    - RealizarPruebasFuncionalesSelenium.groovy
    - RealizarPruebasFuncionalesCypress.groovy (sin implementación)

- Envio resultado subida Artifactory
    - EnvioInformeArtifactory.groovy

### Modificado

Se realizan modificaciones menores para adaptar y corregir funcionamiento sobre:

- EnvioInformeOWASP.groovy
- EnvioInformeSonar.groovy
- EnvioLogCorreo.groovy

### Obsoleto

No aplica

### Eliminado

Sin cambios

## [0.2.0] - (08/04/2020)

La versión 0.1.0  de CadenaUnica se desarrolló con un único Jenkinsfile monolítico. Para no tener problemas de cara a futuro cuando se vaya ampliando la funcionalidad de la cadena, 
se ha visto necesario refactorizar lo desarrollado y enfocarlo para crear módulos y funciones reutilizables, por lo que a partir de esta versión, se crea el proyecto "Eslabones". De momento,
tendrá el mismo versionado que CadenaUnica, por lo que la primera versión de Eslabones es la 0.2.0


### Añadido

- AnalisisOWASP.groovy
- AnalisisOWASPPublicarResultados.groovy
- AnalisisSonar.groovy
- DesplegarArtifactory.groovy
- DesplegarServidorAplicaciones.groovy
- EnvioLogCorreo.groovy
- eslabonMaven.groovy
- LimpiarRepositorio.groovy
- ObtenerCodigoFuente.groovy
- PruebasUnitarias.groovy
- EnvioInformeOWASP.groovy
- DescargarArtifactory.groovy
- EnvioInformeSonar.groovy
- GeneracionSite.groovy
- PublicacionSite.groovy


### Modificado

No aplica

### Obsoleto

No aplica

### Eliminado

No aplica


[0.4.0]: Incluir URL 
[0.3.0]: https://gitlabexp.chap.junta-andalucia.es/ccc/eslabones/-/tags/0.3.0
[0.2.0]: https://gitlabexp.chap.junta-andalucia.es/ccc/eslabones/-/tags/0.2.0









