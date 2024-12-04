package custom.android.plugin.publish.library

import custom.android.plugin.PublishInfoExtension
import custom.android.plugin.publish.BaseLocalPublish
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension

open class MavenPublishLocalLibrary : BaseLocalPublish() {

    companion object {
        private const val TAG = "MavenPublishLibrary"
        const val MAVEN_PUBLICATION_NAME = "EnterPublish"
    }

    override fun queryVersion(): String? {
        return null
    }

    override fun getPublishTaskName(): String {
        return "publishLocalLibrary"
    }

    private fun getLocalRepositoriesUrl(publishing: PublishingExtension) =
        publishing.repositories.mavenLocal().url.toString()

    override fun publishLocal(project: Project, publishInfo: PublishInfoExtension) {
        MavenPublishUtils.mavenPublish(project, this, publishInfo, ":publishToMavenLocal") {
            getLocalRepositoriesUrl(it)
        }
    }


    override fun updateInfo() {

    }

    override fun getList() {

    }

    override fun getInfo() {

    }
}