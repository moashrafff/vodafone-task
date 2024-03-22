
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("maven-publish")
}

android {
    namespace = "com.weather.task"
    compileSdk = BuildConfig.compileSdk

    defaultConfig {
        minSdk = BuildConfig.minSdk

        testInstrumentationRunner = BuildConfig.testInstrumentationRunner
    }

    compileOptions {
        sourceCompatibility = BuildConfig.sourceCompatibility
        targetCompatibility = BuildConfig.targetCompatibility
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildConfig.kotlinCompilerExtensionVersion
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

    implementation(Hilt.hilt)
    implementation(libs.play.services.location)
    kapt(Hilt.hiltAndroidCompiler)
    kapt(Hilt.hiltCompiler)
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
    implementation(kotlin("script-runtime"))
}


val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

afterEvaluate {
    publishing {
        publications {
            val release by publications.registering(MavenPublication::class) {
                from(components["release"])
                artifact(sourcesJar.get())
                artifactId = "lib1"
                groupId = "com.weather.task"
                version = "1.0.2"
            }
        }
    }
}


tasks.withType<Test> {
    useJUnit()
    testLogging {
        events("standardOut", "started", "passed", "skipped", "failed")
        showStandardStreams = true
        /*  outputs.upToDateWhen {
            false
          }*/
    }
}