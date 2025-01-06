package custom.android.plugin.publish.app.fir.im

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import custom.android.plugin.PublishInfoExtension
import custom.android.plugin.publish.BaseLocalPublish
import org.gradle.api.Project

open class FirImPublishLocalApp : BaseLocalPublish() {

    companion object {
        private const val TAG = "FirImPublishApp"
    }

    override fun queryVersion(): String? {
        return ""
    }

    override fun getPublishTaskName(): String {
        return "publishLocalApp"
    }

    override fun publishLocal(project: Project, publishInfo: PublishInfoExtension) {
    /*    val android = project.extensions.findByName("android") as? AppExtension
        // 遍历所有变种
        android?.applicationVariants?.all{
            variant ->
            handleVariant(project, variant)
            true
        }*/

       /* val realTaskName = ":"
        val out = ByteArrayOutputStream()
        val path = "${project.rootDir}${File.separator}${gradlewFileName()}"
        PluginLogUtil.printlnDebugInScreen("$TAG path: $path realTaskName: $realTaskName")
        project.exec {
            standardOutput = out
            setCommandLine(
                path, realTaskName
            )
        }*/
    }


    private fun handleVariant(project: Project, variant: ApplicationVariant) {
        // 获取变种的相关信息
        println("Variant name: ${variant.name}")
        println("Build type: ${variant.buildType.name}")
        println("Flavor: ${variant.flavorName}")
        variant.outputs.forEach {
            val apkFile=it.outputFile
            println("Output APK: ${apkFile.parent}")
        }
    }

    override fun updateInfo() {

    }

    override fun getList() {

    }

    override fun getInfo() {

    }
}