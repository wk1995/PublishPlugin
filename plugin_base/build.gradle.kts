plugins {
    `kotlin-dsl`
    id("android.plugin.baseBuild")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    implementation("com.android.tools.build:gradle:8.1.3")
    implementation("custom.android.plugin:baseBuild:latest.release")
}

PublishInfo{
    groupId = "entertech.android.plugin"
    artifactId = "base"
    version = "0.0.1"
    pluginId="entertech.android.plugin.base"
    implementationClass = "entertech.android.plugin.EnterTechGradlePlugin"
}

