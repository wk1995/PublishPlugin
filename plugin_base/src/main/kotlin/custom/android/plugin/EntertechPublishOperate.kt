package custom.android.plugin

class EntertechPublishOperate:PublishOperate() {

    override fun getDefaultPublishUrl(): String {
        return "https://s01.oss.sonatype.org/content/repositories/releases/"
    }

    override fun getDefaultPublishUserName(): String {
        return "6584HSEW"
    }

    override fun getDefaultPublishPassword(): String {
        return "LlR0Ry9u/czWJlvN8gxqGfpFWfpzLtXjMYhjsnTgjLOq"
    }
}