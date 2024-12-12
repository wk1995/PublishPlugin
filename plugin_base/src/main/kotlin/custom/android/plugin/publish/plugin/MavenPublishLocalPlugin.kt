package custom.android.plugin.publish.plugin

import custom.android.plugin.publish.library.MavenPublishLocalLibrary

open class MavenPublishLocalPlugin: MavenPublishLocalLibrary() {

    override fun getPublishTaskName(): String {
        return "publishLocalPlugin"
    }
}