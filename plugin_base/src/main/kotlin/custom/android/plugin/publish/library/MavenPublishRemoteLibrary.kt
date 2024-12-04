package custom.android.plugin.publish.library

import custom.android.plugin.PublishTask
import custom.android.plugin.PublishInfoExtension
import custom.android.plugin.base.ProjectHelper
import custom.android.plugin.log.PluginLogUtil
import custom.android.plugin.publish.BaseRemotePublish
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import java.io.ByteArrayOutputStream
import java.io.File

open class MavenPublishRemoteLibrary : BaseRemotePublish() {

    companion object {
        private const val TAG = "MavenPublishLibrary"
        const val MAVEN_PUBLICATION_NAME = "EnterPublish"
    }

    //检验状态是否通过
    private var checkStatus = false
    override fun queryVersion(): String? {
        return null
    }

    private fun getRemoteRepositoriesUrl(publishing: PublishingExtension) =
        publishing.repositories.mavenLocal().url.toString()

    override fun getPublishTaskName(): String {
        return "publishRemoteLibrary"
    }

    override fun publishRemote(project: Project, publishInfo: PublishInfoExtension) {
        //1、对publisher配置的信息进行基础校验
        //2、把publisher上传到服务器端，做版本重复性校验
        checkStatus = checkPublishInfo(publishInfo)
        if (checkStatus) {
            MavenPublishUtils.mavenPublish(
                project,
                this,
                publishInfo,
                ":publish${PublishTask.MAVEN_PUBLICATION_NAME}PublicationToMavenRepository"
            ) {
                getRemoteRepositoriesUrl(it)
            }
        }
    }

    private fun checkPublishInfo(publishInfo: PublishInfoExtension): Boolean {
        val version = publishInfo.version
        if (version.endsWith("debug")) {
            PluginLogUtil.printlnErrorInScreen("$publishInfo version end with debug")
            return false
        }
        return true
    }

    override fun updateInfo() {

    }

    override fun getList() {

    }

    override fun getInfo() {

    }
}