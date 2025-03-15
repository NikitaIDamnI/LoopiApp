plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.example.contentdetailsscreen"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    //Jsoup
    implementation(libs.jsoup) // Актуальную версию можно проверить на Maven Central


    //Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Hilt
    implementation(libs.dagger.hilt.android)
    implementation(libs.dagger.hilt.navigation.compose)
    kapt(libs.dagger.hilt.compiler)

    
    implementation(libs.androidx.foundation.layout)
    debugImplementation(libs.androidx.ui.tooling)

    // Для UI-тестирования Compose
    androidTestImplementation(libs.androidx.ui.test.junit4)

    //Coil
    implementation(libs.coil.compose)

    implementation(libs.haze.jetpack.compose)

    implementation(project(":core:di"))
    implementation(project(":core:domain"))
    implementation(project(":uikit"))
    implementation(project(":core:common"))
}
