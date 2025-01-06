package custom.android.plugin.publish.app.fir.im

import com.android.build.gradle.api.ApplicationVariant
import custom.android.plugin.PublishInfoExtension
import custom.android.plugin.log.PluginLogUtil
import custom.android.plugin.publish.BaseRemotePublish
import org.gradle.api.Project
import java.io.ByteArrayOutputStream
//a503cf1a61e7b6afa43234e80fc201f6
open class FirImPublishRemoteApp : BaseRemotePublish() {

    companion object {
        private const val TAG = "FirImPublishApp"
    }

    override fun queryVersion(): String? {
        return ""
    }

    override fun getPublishTaskName(): String {
        return "publishRemoteApp"
    }

    private fun handleVariant(project: Project, variant: ApplicationVariant) {
        // 获取变种的相关信息
        println("Variant name: ${variant.name}")
        println("Build type: ${variant.buildType.name}")
        println("Flavor: ${variant.flavorName}")
        variant.outputs.forEach {
            val apkFile=it.outputFile
            PluginLogUtil.printlnInfoInScreen("Output APK: file://${apkFile.parent}")
        }
    }

    override fun publishRemote(project: Project, publishInfo: PublishInfoExtension) {
        val out = ByteArrayOutputStream()
//        val path = "${project.rootDir}${File.separator}${gradlewFileName()}"
//        PluginLogUtil.printlnDebugInScreen("$TAG path: $path realTaskName: $realTaskName")
//        //通过命令行的方式进行调用上传maven的task
//        project.exec {
//            standardOutput = out
//            setCommandLine(
//                path, realTaskName
//            )
//        }
    }

    override fun updateInfo() {

    }

    override fun getList() {

    }

    override fun getInfo() {

    }
}