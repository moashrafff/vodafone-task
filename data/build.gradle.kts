import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.vodafone_task"
    compileSdk = BuildConfig.compileSdk

    defaultConfig {
        minSdk = BuildConfig.minSdk

        testInstrumentationRunner = BuildConfig.testInstrumentationRunner

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

//        buildConfigField("String", "API_KEY", "7ce5876ab51cd81e802d24301b72ffa0")
    }

    compileOptions {
        sourceCompatibility = BuildConfig.sourceCompatibility
        targetCompatibility = BuildConfig.targetCompatibility
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(BuildConfig.jvmTarget))
        }
    }
    kotlinOptions {
        jvmTarget = BuildConfig.jvmTarget
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(AndroidX.coreKtx)

    // api calls - retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.retrofitConverter)
    implementation(Retrofit.okhttp)
    implementation(Retrofit.logginInterceptor)

    implementation(Hilt.hilt)
    kapt(Hilt.hiltAndroidCompiler)
    kapt(Hilt.hiltCompiler)
    implementation(AndroidX.workManagerKtx)

    // room
    implementation(Room.room)
    implementation(Room.roomKtx)
    kapt(Room.roomKapt)

    // gson
    implementation(Google.gson)

    // coroutines
    implementation(Kotlin.coroutines)
}