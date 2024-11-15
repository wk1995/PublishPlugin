package custom.android.plugin

import custom.android.plugin.base.ModuleType
import custom.android.plugin.base.ProjectHelper
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer

/**
 * 执行publishToMavenLocal
 * */
open class PublishPlugin : Plugin<Project> {
    companion object {
        private const val TAG = "PublishPlugin"
    }

    private val mEntertechPublishOperate by lazy {
        EntertechPublishOperate()
    }

    override fun apply(project: Project) {
        // 应用Gradle官方的Maven插件
        val container = project.plugins
        val type = if (ProjectHelper.isApp(container)) {
            ModuleType.APP
        } else if (ProjectHelper.isPlugin(container)) {
            ModuleType.PLUGIN
        } else {
            ModuleType.LIBRARY
        }
        mEntertechPublishOperate.configPublish(project, type, PublishInfoExtension::class.java)
    }

}