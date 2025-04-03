import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.1.10"
}

android {
    namespace = "com.example.cumulora"
    compileSdk = 35


    val localProperties = Properties().apply {
        load(File(rootProject.projectDir, "local.properties").inputStream())
    }
    val weatherApiKey: String = localProperties.getProperty("WEATHER_API_KEY") ?: ""
    val googleMapsApiKey: String = localProperties.getProperty("GOOGLE_API_KEY") ?: ""

    packagingOptions {
        exclude("META-INF/LICENSE-notice.md")
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE.txt")
    }

    defaultConfig {
        applicationId = "com.example.cumulora"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        buildConfigField("String", "WEATHER_API_KEY", "\"$weatherApiKey\"")
        buildConfigField("String", "googleApiKey", localProperties.getProperty("GOOGLE_API_KEY"))
        resValue("string", "googleApiKey", localProperties.getProperty("GOOGLE_API_KEY"))

    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    val room_version = "2.6.1"
    val nav_version = "2.8.8"
    val compose_version = "1.6.3"
    val work_version = "2.10.0"


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //?Testing===============================================================
    //*Unit
    //!Mockk
    testImplementation("io.mockk:mockk-android:1.13.17")
    testImplementation("io.mockk:mockk-agent:1.13.17")
    testImplementation(libs.junit)
    //!Hamcrest
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.hamcrest:hamcrest-library:2.2")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    //!Robolectric
    testImplementation("androidx.test.ext:junit-ktx:1.1.3")
    testImplementation("androidx.test:core-ktx:1.5.0")
    testImplementation("org.robolectric:robolectric:4.11")
    //!Rule
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    //*Instrumented
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    //!Mockk
    androidTestImplementation("io.mockk:mockk-android:1.13.17")
    androidTestImplementation("io.mockk:mockk-agent:1.13.17")
    //!Hamcrest
    androidTestImplementation("org.hamcrest:hamcrest:2.2")
    androidTestImplementation("org.hamcrest:hamcrest-library:2.2")
    //!Rule
    androidTestImplementation ("androidx.arch.core:core-testing:2.1.0")
    //?=========================================================================

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    //Room
    implementation("androidx.room:room-runtime:$room_version")
    //*Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")
    //*optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    //WorkManager
    implementation("androidx.work:work-runtime-ktx:$work_version")
    implementation("androidx.work:work-runtime:$work_version")

    //view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose-android:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    //Navigation
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

    //Maps
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps.android:maps-compose:4.4.1")
    //*Places
    implementation("com.google.maps.android:places-compose:0.1.2")
    implementation("com.google.android.libraries.places:places:3.1.0")

    //UI
    implementation("androidx.compose.material:material-icons-extended:$compose_version")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

    //Glide & lottie
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")
    implementation("com.airbnb.android:lottie-compose:6.1.0")

    //Media player
    implementation("androidx.media3:media3-exoplayer:1.2.1")
    implementation("androidx.media3:media3-ui:1.2.1")


}