package entertech.android.plugin

import custom.android.plugin.push.PublishInfoExtension

open class EnterPublishInfoExtension: PublishInfoExtension() {
    override fun getPublishPassword(): String {
        return "5k1Kj4x6We/bPKTS7Ni/6ZZwL2FGnTNCkgNFlhWzXz6D"
    }

    override fun getPublishUrl(): String {
        return "https://s01.oss.sonatype.org/content/repositories/releases/"
    }

    override fun getPublishUserName(): String {
        return "2nqgfmsz"
    }
}