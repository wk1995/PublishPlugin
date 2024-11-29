package custom.android.plugin.publish.app.fir.im

import custom.android.plugin.PublishInfoExtension
import custom.android.plugin.publish.app.BasePublishApp
import org.gradle.api.Project
import java.io.ByteArrayOutputStream

open class FirImPublishApp : BasePublishApp() {

    companion object {
        private const val TAG = "FirImPublishApp"
    }

    override fun queryVersion(): String? {
        return ""
    }

    override fun publishLocal(project: Project, publishInfo: PublishInfoExtension) {

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