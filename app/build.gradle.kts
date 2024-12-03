plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("custom.android.plugin")
}

android {
    namespace = "cn.entertech.entertech.plugin"
    compileSdk = 34

    defaultConfig {
        applicationId = "cn.entertech.entertech.plugin"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions.add("device")
    flavorDimensions.add("type")

    productFlavors {
        create("phone") {
            dimension = "device"
            applicationIdSuffix = ".phone"
            versionNameSuffix = "-phone"
        }
        create("free") {
            dimension = "type"
            applicationIdSuffix = ".free"
            versionNameSuffix = "-free"
        }

        create("vip") {
            dimension = "type"
            applicationIdSuffix = ".vip"
            versionNameSuffix = "-vip"
        }
        create("paid") {
            dimension = "device"
            applicationIdSuffix = ".paid"
            versionNameSuffix = "-paid"
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("other") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}