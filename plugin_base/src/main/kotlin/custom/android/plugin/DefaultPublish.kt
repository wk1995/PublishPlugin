package custom.android.plugin

import custom.android.plugin.publish.BasePublish
import org.gradle.api.Project

open class DefaultPublish: BasePublish() {

    override fun queryVersion(): String? {
        return ""
    }

    override fun publishLocal(project: Project, publishInfo: PublishInfoExtension) {
    }

    override fun publishRemote(project: Project, publishInfo: PublishInfoExtension) {
    }

    override fun updateInfo() {
    }

    override fun getList() {
    }

    override fun getInfo() {
    }
}