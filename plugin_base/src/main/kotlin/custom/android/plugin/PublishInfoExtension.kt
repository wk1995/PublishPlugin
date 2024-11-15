package custom.android.plugin

open class PublishInfoExtension() {

    companion object {
        const val EXTENSION_PUBLISH_INFO_NAME = "PublishInfo"
    }

    constructor(groupId: String, artifactId: String, version: String) : this() {
        this.groupId = groupId
        this.artifactId = artifactId
        this.version = version
    }

    constructor(
        groupId: String,
        artifactId: String,
        version: String,
        pluginId: String,
        implementationClass: String
    ) : this() {
        this.groupId = groupId
        this.artifactId = artifactId
        this.version = version
        this.pluginId = pluginId
        this.implementationClass = implementationClass
    }

    constructor(
        groupId: String,
        artifactId: String,
        version: String,
        pluginId: String,
        implementationClass: String,
        publishUrl: String,
        publishUserName: String,
        publishPassword: String
    ) : this() {
        this.groupId = groupId
        this.artifactId = artifactId
        this.version = version
        this.pluginId = pluginId
        this.implementationClass = implementationClass
        this.publishUrl = publishUrl
        this.publishUserName = publishUserName
        this.publishPassword = publishPassword
    }


    /**
     * 包名
     */
    var groupId = ""

    /**
     * 项目名
     */
    var artifactId = ""

    /**
     * 版本号
     */
    var version = ""

    var pluginId = ""

    var implementationClass = ""

    var publishUrl: String = ""

    var publishUserName: String = ""

    var publishPassword: String = ""

    var signingKeyId: String = ""
    var signingSecretKey: String = ""
    var signingPassword: String = ""
}