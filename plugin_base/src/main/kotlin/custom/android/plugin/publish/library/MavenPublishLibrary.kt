package custom.android.plugin.publish.library

import custom.android.plugin.BasePublishTask
import custom.android.plugin.PublishInfoExtension
import custom.android.plugin.base.ProjectHelper
import custom.android.plugin.log.PluginLogUtil
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import java.io.ByteArrayOutputStream
import java.io.File

open class MavenPublishLibrary : BasePublishLibrary() {

    companion object {
        private const val TAG = "MavenPublishLibrary"
        const val MAVEN_PUBLICATION_NAME = "EnterPublish"
    }

    override fun queryVersion(): String? {
        return null
    }

    private fun getLocalRepositoriesUrl(publishing: PublishingExtension) =
        publishing.repositories.mavenLocal().url.toString()

    private fun getRemoteRepositoriesUrl(publishing: PublishingExtension) =
        publishing.repositories.mavenLocal().url.toString()

    private fun mavenPublish(
        project: Project,
        publishInfo: PublishInfoExtension,
        taskName: String,
        getRepositoriesUrl: (PublishingExtension) -> String
    ) {
        val realTaskName = ":${project.name}$taskName"
        val out = ByteArrayOutputStream()
        val path = "${project.rootDir}${File.separator}${gradlewFileName()}"
        PluginLogUtil.printlnDebugInScreen("$TAG path: $path realTaskName: $realTaskName")
        //通过命令行的方式进行调用上传maven的task
        project.exec {
            standardOutput = out
            setCommandLine(
                path, realTaskName
            )
        }
        val result = out.toString()
        PluginLogUtil.printlnInfoInScreen("==================================================================")
        PluginLogUtil.printlnDebugInScreen("maven 上传结果   :   $result")
        PluginLogUtil.printlnInfoInScreen("==================================================================")
        if (result.contains("UP-TO-DATE")) {
            //上传maven仓库成功，上报到服务器
            val publishing = project.extensions.getByType(PublishingExtension::class.java)
            var groupId = ""
            var artifactId = ""
            var version = ""
            publishing.publications {
                val mavenPublication = getByName(MAVEN_PUBLICATION_NAME) as MavenPublication
                groupId = mavenPublication.groupId
                artifactId = mavenPublication.artifactId
                version = mavenPublication.version

            }
            val fileNames = groupId.split(".")
            val pathSb = StringBuilder()
            pathSb.append(getRepositoriesUrl(publishing))
            fileNames.forEach {
                pathSb.append(it)
                pathSb.append(File.separatorChar)
            }

            PluginLogUtil.printlnInfoInScreen("构建成功")
            PluginLogUtil.printlnInfoInScreen("仓库地址：  $pathSb")
            PluginLogUtil.printlnInfoInScreen("===================================================================")
            PluginLogUtil.printlnInfoInScreen("")
            if (ProjectHelper.isLibrary(project.plugins)) {
                PluginLogUtil.printlnInfoInScreen("implementation (\"$groupId:$artifactId:$version\")")
            } else {
                PluginLogUtil.printlnInfoInScreen("classpath (\"$groupId:$artifactId:$version\")")
                PluginLogUtil.printlnInfoInScreen("")
                PluginLogUtil.printlnInfoInScreen("id(\"${publishInfo.pluginId}\")")
            }

            PluginLogUtil.printlnInfoInScreen("")
            PluginLogUtil.printlnInfoInScreen("==================================================================")
            //提示成功信息
        } else {
            throw Exception("上传Maven仓库失败，请检查配置！")
        }
        PluginLogUtil.printlnDebugInScreen("$TAG executeTask finish ")
    }

    override fun publishLocal(project: Project, publishInfo: PublishInfoExtension) {
        mavenPublish(project, publishInfo, ":publishToMavenLocal") {
            getLocalRepositoriesUrl(it)
        }
    }

    override fun publishRemote(project: Project, publishInfo: PublishInfoExtension) {
        mavenPublish(
            project,
            publishInfo,
            ":publish${BasePublishTask.MAVEN_PUBLICATION_NAME}PublicationToMavenRepository"
        ) {
            getRemoteRepositoriesUrl(it)
        }
    }

    override fun updateInfo() {

    }

    override fun getList() {

    }

    override fun getInfo() {

    }
}