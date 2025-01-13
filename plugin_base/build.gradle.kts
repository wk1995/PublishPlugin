plugins {
    `kotlin-dsl`
    id("maven-publish")
//    id("signing")
}

dependencies {
    //gradle sdk
    implementation(gradleApi())
    //groovy sdk
    implementation(localGroovy())
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    implementation("com.android.tools.build:gradle:8.1.3")
}

gradlePlugin {
    plugins {
        create("customPlugin") {
            // 插件ID
            id = "custom.android.plugin"
            // 插件的实现类
            implementationClass = "custom.android.plugin.PublishPlugin"
        }
    }

}

publishing {
    publications {
        create<MavenPublication>("customPlugin") {
            groupId = "cn.entertech.android"
            artifactId = "publish"
            version = "1.1.0-local"
            from(components["java"])
        }
    }/*   signing {
           useInMemoryPgpKeys(
               findProperty("signing.keyId") as String?,
               findProperty("signing.secretKeyRingFile") as String?,
               findProperty("signing.password") as String?
           )
           sign(publishing.publications["customPlugin"])
       }*/

    repositories {
        maven {
            //允许使用 http
            isAllowInsecureProtocol = true
            setUrl("https://s01.oss.sonatype.org/content/repositories/releases/")
            credentials {
                username = "6584HSEW"
                password = "LlR0Ry9u/czWJlvN8gxqGfpFWfpzLtXjMYhjsnTgjLOq"
            }
        }
    }

}
