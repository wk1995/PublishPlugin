package custom.android.plugin.publish

import custom.android.plugin.PublishInfoExtension
import org.gradle.api.Project

abstract class BaseRemotePublish : BasePublish() {

    /**
     * 发布到云端
     * */
    abstract fun publishRemote(project: Project, publishInfo: PublishInfoExtension)

    override fun publish(project: Project, publishInfo: PublishInfoExtension) {
        publishRemote(project, publishInfo)
    }
}