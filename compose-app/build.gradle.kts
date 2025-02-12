import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

buildscript {
    dependencies {
        classpath(libs.kotlinpoet)
    }
}

fun buildConfigSourceDirectory() =
    layout.buildDirectory.dir("generated/buildconfig/kotlin")

val generateBuildConfig: TaskProvider<*> by tasks.registering {
    val packageName = "com.github.haloperidozz.obfuscator.buildconfig"
    val outputDirectory = buildConfigSourceDirectory()

    outputs.dir(outputDirectory)

    doLast {
        val buildConfig = TypeSpec.objectBuilder("BuildConfig")
            .addProperty(
                PropertySpec.builder("VERSION", String::class)
                    .initializer("%S", project.version.toString())
                    .addModifiers(KModifier.CONST)
                    .build()
            )
            .addProperty(
                PropertySpec.builder("GITHUB_URL", String::class)
                    .initializer("%S", project.findProperty("github") ?: "")
                    .addModifiers(KModifier.CONST)
                    .build()
            )
            .build()

        val file = FileSpec.builder(packageName, "BuildConfig")
            .addType(buildConfig)
            .build()

        file.writeTo(outputDirectory.get().asFile)
    }
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "kvas-obfusactor"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "kvas-obfusactor.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.androidx.datastore)
            implementation(libs.androidx.datastore.preferences)
        }
        commonMain {
            kotlin.srcDir(buildConfigSourceDirectory())

            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(compose.materialIconsExtended)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
            }
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.androidx.datastore)
            implementation(libs.androidx.datastore.preferences)
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

android {
    namespace = "com.github.haloperidozz.obfuscator"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.github.haloperidozz.obfuscator"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionName = "${project.version}"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose.desktop {
    application {
        mainClass = "com.github.haloperidozz.obfuscator.MainKt"

        buildTypes.release.proguard {
            configurationFiles.from(project.file("desktop/proguard-rules.pro"))
        }

        nativeDistributions {
            targetFormats(TargetFormat.Msi, TargetFormat.Deb)

            packageName = "kvas-obfusactor"
            packageVersion = project.version.toString().run {
                if (count { it == '.' } == 1) "$this.0" else this
            }

            linux {
                iconFile = project.file("desktop/app-icon.png")
            }

            windows {
                menuGroup = "haloperidozz"
                upgradeUuid = "bfec101b-c560-46cd-b218-cc261b079924"
                shortcut = true
                iconFile = project.file("desktop/app-icon.ico")
            }
        }
    }
}

// HACK: A reliable way to generate BuildConfig ;D
tasks.generateResourceAccessorsForCommonMain {
    dependsOn(generateBuildConfig)
}