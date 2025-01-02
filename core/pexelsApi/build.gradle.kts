plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
   // alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kapt)


}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies{
    //Kotlinx
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.annotation)

//    //Hilt
//    implementation(libs.dagger.hilt.android)
//    kapt(libs.dagger.hilt.compiler)



    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.adapters.result)
    implementation(libs.gson)
    api(libs.okhttp)
    kapt(libs.retrofit.responseTypeKeeper)



}



