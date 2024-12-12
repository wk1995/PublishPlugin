package custom.android.plugin.publish

import custom.android.plugin.PublishInfoExtension
import custom.android.plugin.log.PluginLogUtil
import org.gradle.api.Project

abstract class BasePublish {

    /**
     * 版本查询
     * */
    abstract fun queryVersion(): String?


    abstract fun publish(project: Project, publishInfo: PublishInfoExtension)

    abstract fun getPublishTaskName():String

    /**
     * 修改信息
     * */
    abstract fun updateInfo()

    /**
     * 获取列表
     * */
    abstract fun getList()


    /**
     * 查看详细信息
     * */
    abstract fun getInfo()

    fun gradlewFileName(): String {
        val osName = System.getProperty("os.name")
        PluginLogUtil.printlnInfoInScreen("current System is :$osName")
        return if (osName.contains("Windows")) {
            // Windows 系统
            "gradlew.bat"
        } else if (osName.contains("Mac")) {
            // macOS 系统
            "gradlew"
        } else if (osName.contains("Linux")) {
            // Linux 系统
            "gradlew.bat"
        } else {
            ""
        }
    }
}