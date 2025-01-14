plugins {
    id("com.android.application")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.weather_task"
    compileSdk = BuildConfig.compileSdk

    defaultConfig {
        applicationId = BuildConfig.applicationId
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName

        testInstrumentationRunner = BuildConfig.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isDebuggable = false
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = BuildConfig.sourceCompatibility
        targetCompatibility = BuildConfig.targetCompatibility
    }
    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(BuildConfig.jvmTarget))
        }
    }
    kotlinOptions {
        jvmTarget = BuildConfig.jvmTarget
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildConfig.kotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(project(":data"))
    implementation(project(":core"))
    implementation(project(":features:home"))
    implementation(project(":features:search"))
    implementation(project(":features:forecast"))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleRuntimeKtx)

    implementation(Hilt.hilt)
    implementation(Hilt.hiltNavigationCompose)
    implementation(Hilt.hiltWork)
    kapt(Hilt.hiltAndroidCompiler)
    kapt(Hilt.hiltCompiler)

    implementation(AndroidX.activityCompose)
    implementation(AndroidX.navigationCompose)
    implementation(AndroidX.workManager)
    implementation(AndroidX.workManagerKtx)

    implementation(platform(Compose.composeBom))
    implementation(Compose.composeUi)
    implementation(Compose.composeGraphics)
    implementation(Compose.composeUiTooling)
    implementation(Compose.composeToolingPreview)
    implementation(Compose.composeMaterial3)
    implementation(Compose.composeMaterial3WindowSize)
    implementation(Compose.composeRuntimeLivedata)
    implementation(Compose.viewModelCompose)

    implementation(Google.gson)
    implementation(Google.material)
}