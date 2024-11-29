package custom.android.plugin

import custom.android.plugin.publish.BasePublish
import org.gradle.api.publish.PublishingExtension
import javax.inject.Inject

/**
 * 如果不写成open，会报找不到这个类的错误
 * */
open class PublishLibraryLocalTask @Inject constructor(publish: BasePublish) :
    BasePublishTask(publish) {

    companion object {
        const val TAG = "PublishLibraryLocalTask"
    }

    override fun executeTask(publishInfo: PublishInfoExtension) {
        publish.publishLocal(project, publishInfo)
    }

    override fun initPublishCommandLine() = ":publishToMavenLocal"

    override fun fetchTaskName(): String = TAG
}