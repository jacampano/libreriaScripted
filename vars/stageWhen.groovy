
import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def call(String stageName, Boolean whenCondition, body) {
    stage(stageName) {
        if (whenCondition) {
            body()
         } else {
            Utils.markStageSkippedForConditional(stageName)
        }
    }
}
