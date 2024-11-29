package custom.android.plugin

import custom.android.plugin.log.PluginLogUtil
import custom.android.plugin.publish.BasePublish
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class BasePublishTask @Inject constructor(@get:Input protected val publish: BasePublish) :
    DefaultTask() {

    /**
     * 不能写成get/set
     * */
    abstract fun initPublishCommandLine(): String

    companion object {
        private const val TAG = "BasePublishTask"
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

    abstract fun executeTask(publishInfo: PublishInfoExtension)

    /**
     * 上报服务器进行版本检查,这里直接模拟返回成功
     * */
    protected open fun checkPublishInfo(publishInfo: PublishInfoExtension): Boolean {
        return true
    }

    /**
     * 上报服务器进行版本更新操作,这里直接模拟返回成功
     * */
    private fun requestUploadVersion(): Boolean {
        return true
    }

    abstract fun fetchTaskName(): String
}