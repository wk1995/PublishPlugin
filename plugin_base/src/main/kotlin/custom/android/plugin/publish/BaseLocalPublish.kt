package custom.android.plugin.publish

import custom.android.plugin.PublishInfoExtension
import org.gradle.api.Project

abstract class BaseLocalPublish :BasePublish() {

    /**
     * 发布到本地
     * */
    abstract fun publishLocal(project: Project, publishInfo: PublishInfoExtension)

    override fun publish(project: Project, publishInfo: PublishInfoExtension) {
        publishLocal(project, publishInfo)
    }
}