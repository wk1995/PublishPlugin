plugins {
    `kotlin-dsl`
    id("android.plugin.baseBuild")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    implementation("com.android.tools.build:gradle:8.1.3")
    api("custom.android.plugin:baseBuild:latest.release")
}

PublishInfo {
    groupId = "cn.entertech.android"
    artifactId = "plugin"
    version = "0.0.2"
    pluginId = "cn.entertech.android.plugin.base"
    implementationClass = "entertech.android.plugin.EnterTechGradlePlugin"
    setPublishUrl("https://s01.oss.sonatype.org/content/repositories/releases/")
    setPublishPassword("5k1Kj4x6We/bPKTS7Ni/6ZZwL2FGnTNCkgNFlhWzXz6D")
    setPublishUserName("2nqgfmsz")
}

