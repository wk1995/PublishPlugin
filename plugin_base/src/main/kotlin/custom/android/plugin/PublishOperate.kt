package custom.android.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.ApplicationVariant
import custom.android.plugin.PublishTask.Companion.MAVEN_PUBLICATION_NAME
import custom.android.plugin.base.ModuleType
import custom.android.plugin.log.PluginLogUtil
import custom.android.plugin.publish.app.fir.im.FirImPublishLocalApp
import custom.android.plugin.publish.app.fir.im.FirImPublishRemoteApp
import custom.android.plugin.publish.library.MavenPublishLocalLibrary
import custom.android.plugin.publish.library.MavenPublishRemoteLibrary
import custom.android.plugin.publish.plugin.MavenPublishLocalPlugin
import custom.android.plugin.publish.plugin.MavenPublishRemotePlugin
import org.gradle.api.Project
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.get
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.gradle.plugins.signing.SigningExtension
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.util.Properties


open class PublishOperate {
    companion object {
        private const val TAG = "PublishOperate"
        private const val ASSEMBLE = "assemble"
    }

    private val properties by lazy {
        Properties()// local.properties file in the root director
    }

    fun <T : PublishInfoExtension> configPublish(project: Project, type: Int, clazz: Class<T>) {
        // use Gradle Maven plugins
        project.extensions.create(
            PublishInfoExtension.EXTENSION_PUBLISH_INFO_NAME, clazz,
        )
        if (type == ModuleType.APP) {
            PluginLogUtil.printlnDebugInScreen("$TAG is app")
            val currProjectName = project.displayName
            PluginLogUtil.printlnDebugInScreen("$TAG currProjectName $currProjectName")
            val android = project.extensions.findByName("android") as? AppExtension
            project.afterEvaluate {
                // 遍历所有变种
                android?.applicationVariants?.all { variant ->
                    val publishTaskName = "生成本地包：${variant.name}"
                    val localPublish = object : FirImPublishLocalApp() {
                        override fun getPublishTaskName(): String {
                            return publishTaskName
                        }

                        override fun publishLocal(
                            project: Project, publishInfo: PublishInfoExtension
                        ) {
                            super.publishLocal(project, publishInfo)
                            assemble(project, gradlewFileName(), variant)
                        }
                    }
                    val remotePublishTaskName = "上传包：${variant.name}"
                    val remotePublish = object : FirImPublishRemoteApp() {
                        override fun getPublishTaskName(): String {
                            return remotePublishTaskName
                        }

                        override fun publishRemote(
                            project: Project, publishInfo: PublishInfoExtension
                        ) {
                            super.publishRemote(project, publishInfo)
                            assemble(project, gradlewFileName(), variant, true)
                        }
                    }
                    project.tasks.register(
                        publishTaskName, PublishTask::class.java, localPublish
                    )
                    project.tasks.register(
                        remotePublishTaskName, PublishTask::class.java, remotePublish
                    )
                    true
                }
            }
            return
        }
        project.plugins.apply(MavenPublishPlugin::class.java)
        project.afterEvaluate {
            try {
                val publishInfo = project.extensions.getByType(clazz)
                val publishing = project.extensions.getByType(PublishingExtension::class.java)
                components.forEach {
                    PluginLogUtil.printlnDebugInScreen("$TAG name: ${it.name}")
                    if (type == ModuleType.PLUGIN) {
                        if (it.name == "java") {
                            val gradlePluginDevelopmentExtension =
                                project.extensions.getByType(GradlePluginDevelopmentExtension::class.java)
                            gradlePluginDevelopmentExtension.plugins {
                                create("gradlePluginCreate") {
                                    // 插件ID
                                    id = publishInfo.pluginId
                                    // 插件的实现类
                                    implementationClass = publishInfo.implementationClass
                                }
                            }
                            publishing(project, publishing, publishInfo, it)
                        }
                    }
                    if (type == ModuleType.LIBRARY) {
                        if (it.name == "release") {
                            //注册上传task
                            publishing(project, publishing, publishInfo, it)
                        }
                    }
                }

            } catch (e: Exception) {
                PluginLogUtil.printlnErrorInScreen("$TAG PluginModule error ${e.message}")
            }


        }
        val currProjectName = project.displayName
        PluginLogUtil.printlnDebugInScreen("$TAG currProjectName $currProjectName")
        project.gradle.afterProject {
            PluginLogUtil.printlnDebugInScreen("$TAG currProject.displayName $displayName")
            if (currProjectName == displayName) {
                PluginLogUtil.printlnDebugInScreen("$TAG $currProjectName 开始注册task ")
                //构造不同的basePublish，taskName也需要basePublish提供，basePublish需要区分local&remote，各种变种
                when (type) {
                    ModuleType.PLUGIN -> {
                        listOf(
                            MavenPublishLocalPlugin(), MavenPublishRemotePlugin()
                        ).forEach {
                            val publishTaskName = it.getPublishTaskName()
                            try {
                                project.tasks.register(
                                    publishTaskName, PublishTask::class.java, it
                                )
                            } catch (e: Exception) {
                                PluginLogUtil.printlnErrorInScreen("$TAG register $publishTaskName error ${e.message} ")
                            }
                        }
                    }

                    ModuleType.LIBRARY -> {
                        listOf(
                            MavenPublishLocalLibrary(), MavenPublishRemoteLibrary()
                        ).forEach {
                            val publishTaskName = it.getPublishTaskName()
                            try {
                                project.tasks.register(
                                    publishTaskName, PublishTask::class.java, it
                                )
                            } catch (e: Exception) {
                                PluginLogUtil.printlnErrorInScreen("$TAG register $publishTaskName error ${e.message} ")
                            }
                        }
                    }
                }

            }
        }
    }

    private fun assemble(
        project: Project,
        gradlewFileName: String,
        variant: ApplicationVariant,
        upload: Boolean = false
    ) {
        val path = "${project.rootDir}${File.separator}$gradlewFileName"
        val out = ByteArrayOutputStream()
        project.exec {
            standardOutput = out
            setCommandLine(
                path, "assemble${variant.name}"
            )
        }
        val result = out.toString()
        PluginLogUtil.printlnDebugInScreen("==================================================================")
        PluginLogUtil.printlnDebugInScreen("打包 结果   :   $result")
        PluginLogUtil.printlnDebugInScreen("==================================================================")
        variant.outputs.forEach { it ->
            val apkFile = it.outputFile
            PluginLogUtil.printlnInfoInScreen("Output APK: ${apkFile.parent}")
            //上传包
            if (upload) {

                if (apkFile.exists()) {
                    try {
                        val applicationId = variant.applicationId
                        // 请求 URL
                        val url = URL("http://api.appmeta.cn/apps")

                        // 请求体
                        val jsonData = """
            {
                "type": "android",
                "bundle_id": "com.entertech.tes.vr",
                "api_token": "a503cf1a61e7b6afa43234e80fc201f6"
            }
        """.trimIndent()

                        // 打开 HTTP 连接
                        val connection = url.openConnection() as HttpURLConnection
                        connection.requestMethod = "POST"
                        connection.setRequestProperty("Content-Type", "application/json")
                        connection.doOutput = true

                        // 写入数据
                        connection.outputStream.use { outputStream ->
                            outputStream.write(jsonData.toByteArray(Charsets.UTF_8))
                        }

                        // 获取响应
                        val responseCode = connection.responseCode
                        println("Response Code: $responseCode")

                        // 读取响应内容
                        val response = connection.inputStream.bufferedReader().use { it.readText() }
                        println("Response Body: $response")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } else {
                    PluginLogUtil.printlnErrorInScreen("包不存在 无法上传")
                }
            }
        }
    }

    private fun <T : PublishInfoExtension> publishing(
        project: Project,
        publishing: PublishingExtension,
        publishInfo: T,
        softwareComponent: SoftwareComponent
    ) {
        properties.load(project.rootProject.file("local.properties").inputStream())
        PluginLogUtil.printlnDebugInScreen("properties: $properties")
        val keyId = publishInfo.signingKeyId
        val signingSecretKey = publishInfo.signingSecretKey
        val signingPassword = publishInfo.signingPassword
        if (keyId.isNotEmpty() && signingPassword.isNotEmpty() && signingSecretKey.isNotEmpty()) {
            val signing = project.extensions.getByType(SigningExtension::class.java)
            signing.useInMemoryPgpKeys(keyId, signingPassword, signingSecretKey)
            signing.sign(publishing.publications[MAVEN_PUBLICATION_NAME])
        }

        publishing.publications {
            create(
                MAVEN_PUBLICATION_NAME, MavenPublication::class.java
            ) {
                groupId = publishInfo.groupId
                artifactId = publishInfo.artifactId
                version = publishInfo.version
                if (version.endsWith("-debug")) {
                    val taskName = "androidSourcesJar"
                    //获取build.gradle中的android节点
                    val androidSet = project.extensions.getByName("android") as LibraryExtension
                    val sourceSet = androidSet.sourceSets
                    //获取android节点下的源码目录
                    val sourceSetFiles = sourceSet.findByName("main")?.java?.srcDirs
                    val task = project.tasks.findByName(taskName) ?: project.tasks.create(
                        taskName, Jar::class.java
                    ) {
                        from(sourceSetFiles)
                        archiveClassifier.set("sources")
                    }
                    artifact(task)
                }
                from(softwareComponent)
            }
        }
        var publishUrl = publishInfo.publishUrl
        if (publishUrl.isEmpty()) {
            publishUrl = properties.getProperty("publishUrl", "")
        }
        var publishUserName = publishInfo.publishUserName
        if (publishUserName.isEmpty()) {
            publishUserName = properties.getProperty("publishUserName", "")
        }
        var publishPassword = publishInfo.publishPassword
        if (publishPassword.isEmpty()) {
            publishPassword = properties.getProperty("publishPassword", "")
        }
        PluginLogUtil.printlnDebugInScreen("$TAG publishUrl is $publishUrl")
        PluginLogUtil.printlnDebugInScreen("$TAG publishUserName is $publishUserName  publishPassword is $publishPassword")
        if (publishUrl.isEmpty()) {
            publishUrl = getDefaultPublishUrl()
        }
        if (publishUserName.isEmpty()) {
            publishUserName = getDefaultPublishUserName()
        }
        if (publishPassword.isEmpty()) {
            publishPassword = getDefaultPublishPassword()
        }
        if (publishUrl.isNotEmpty()) {
            publishing.repositories {
                maven {
                    url = URI(publishUrl)
                    isAllowInsecureProtocol = true
                    credentials { ->
                        username = publishUserName
                        password = publishPassword
                    }
                }
            }
        }
    }

    open fun getDefaultPublishUrl(): String = ""

    open fun getDefaultPublishUserName(): String = ""

    open fun getDefaultPublishPassword(): String = ""

}