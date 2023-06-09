import es.curso.CadenaConfig

@NonCPS
def call () {
   
   String directory = env.WORKSPACE
   File  currentDir = new File(directory)
   final String expectedFilePattern = /\Dockerfile/
   def files = []

   currentDir.eachFileRecurse(groovy.io.FileType.FILES) {
      if (it.name ==~ expectedFilePattern) {
         files << it
      }
   }
   
   return files
   
}