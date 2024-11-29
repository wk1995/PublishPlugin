package custom.android.plugin

import custom.android.plugin.log.PluginLogUtil
import custom.android.plugin.publish.BasePublish
import org.gradle.api.publish.PublishingExtension
import javax.inject.Inject


open class PublishLibraryRemoteTask @Inject constructor(publish: BasePublish) :
    BasePublishTask(publish) {
    //检验状态是否通过
    private var checkStatus = false
    companion object {
        const val TAG = "PublishLibraryRemoteTask"
    }

    override fun initPublishCommandLine() =
        ":publish${MAVEN_PUBLICATION_NAME}PublicationToMavenRepository"


    override fun executeTask(publishInfo: PublishInfoExtension) {
        //1、对publisher配置的信息进行基础校验
        //2、把publisher上传到服务器端，做版本重复性校验
        checkStatus = checkPublishInfo(publishInfo)
        if (checkStatus) {
            publish.publishRemote(project,publishInfo)
        }
    }

    override fun checkPublishInfo(publishInfo: PublishInfoExtension): Boolean {
        val version = publishInfo.version
        if (version.endsWith("debug")) {
            PluginLogUtil.printlnErrorInScreen("$publishInfo version end with debug")
            return false
        }
        return true
    }

    override fun fetchTaskName(): String = TAG
}