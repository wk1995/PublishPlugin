package custom.android.plugin

import custom.android.plugin.log.PluginLogUtil
import custom.android.plugin.publish.BasePublish
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

open class PublishTask @Inject constructor(@get:Input protected val publish: BasePublish) :
    DefaultTask() {

    companion object {
        private const val TAG = "PublishTask"
        const val MAVEN_PUBLICATION_NAME = "EnterPublish"
    }

    init {
        group = "customPlugin"
        PluginLogUtil.printlnDebugInScreen("publish: $publish")
    }


    @TaskAction
    fun doTask() {
        executeTask(project.extensions.getByType(PublishInfoExtension::class.java))
    }

    open fun executeTask(publishInfo: PublishInfoExtension){
        publish.publish(project,publishInfo)
    }
}