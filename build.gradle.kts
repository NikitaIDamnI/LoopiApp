import io.gitlab.arturbosch.detekt.extensions.DetektExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.baselineprofile) apply false
    alias(libs.plugins.parcelize) apply false

}




subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {

        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
            freeCompilerArgs.addAll(
                listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                            project.layout.buildDirectory.get().asFile.absolutePath + "/compose_metrics",
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                            project.layout.buildDirectory.get().asFile.absolutePath + "/compose_metrics"
                )
            )
        }
    }
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
            freeCompilerArgs.addAll(
                listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                            project.layout.buildDirectory.get().asFile.absolutePath + "/compose_metrics",
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                            project.layout.buildDirectory.get().asFile.absolutePath + "/compose_metrics"
                )
            )
        }
    }
}

allprojects.onEach { project ->
    project.afterEvaluate {
        with(project.plugins) {
            if (hasPlugin(libs.plugins.kotlin.android.get().pluginId) ||
                hasPlugin(libs.plugins.jetbrains.kotlin.jvm.get().pluginId)
            ) {
                apply(libs.plugins.detekt.get().pluginId)

                project.extensions.configure<DetektExtension> {
                    config.setFrom(rootProject.files("default-detekt-config.yml"))
                }

                // Добавляем стандартные правила Detekt
                project.dependencies.add("detektPlugins", libs.detekt.formatting.get().toString())

                // Добавляем правила для Jetpack Compose
                if (hasPlugin(libs.plugins.kotlin.compose.get().pluginId)) {
                    project.dependencies.add(
                        "detektPlugins",
                        libs.detekt.rules.compose.get().toString()
                    )
                }

                // ✅ Добавляем `decompose-detekt-rules`
                project.dependencies.add(
                    "detektPlugins",
                    "io.github.ajiekcx.detekt:decompose-detekt-rules:0.2.0"
                )
            }
        }
    }
}

