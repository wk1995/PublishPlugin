package custom.android.plugin.publish.plugin

import custom.android.plugin.publish.library.MavenPublishLocalLibrary

open class MavenPublishRemotePlugin : MavenPublishLocalLibrary() {

    override fun getPublishTaskName(): String {
        return "publishRemotePlugin"
    }
}