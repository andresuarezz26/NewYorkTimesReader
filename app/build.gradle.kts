plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
  id("kotlin-parcelize")
  id("androidx.room")
}

android {
  namespace = "com.newyorktimesreader"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.newyorktimesreader"
    minSdk = 23
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  room {
    schemaDirectory("$projectDir/schemas")
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
    compose = true
  }

}

dependencies {

  // Presentation
  implementation(libs.androidx.navigation.compose)

  // Compose
  implementation("androidx.compose.runtime:runtime-livedata:1.6.0")
  androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.10.0")
  debugImplementation("androidx.compose.ui:ui-test-manifest:1.10.0")

  // Coil
  implementation("io.coil-kt.coil3:coil-compose:3.0.4")
  implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")

  // General
  implementation(libs.kotlinx.serialization.json)

  // Hilt
  implementation("com.google.dagger:hilt-android:2.57.1")
  ksp("com.google.dagger:hilt-android-compiler:2.57.1")
  implementation(libs.hilt.navigation.compose)


  // RxKotlin
  implementation("io.reactivex.rxjava3:rxjava:3.0.2")
  implementation("io.reactivex.rxjava3:rxkotlin:3.0.0")
  implementation("io.reactivex.rxjava3:rxandroid:3.0.2")

  // Mockito
  testImplementation("org.mockito:mockito-core:5.10.0")
  testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
  androidTestImplementation("org.mockito:mockito-android:5.10.0")

  // Data Layer

  // Retrofit
  implementation(libs.retrofit)
  implementation(libs.retrofit.converter.gson)

  // Retrofit adapter
  implementation("com.squareup.retrofit2:adapter-rxjava3:3.0.0")

  // Logging interceptor
  implementation(libs.httpLoggingInterceptor)

  // Room
  val room_version = "2.8.4"

  implementation("androidx.room:room-runtime:$room_version")

  // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
  // See Add the KSP plugin to your project
  ksp("androidx.room:room-compiler:$room_version")

  // optional - RxJava3 support for Room
  implementation("androidx.room:room-rxjava3:${room_version}")

  // optional - Test helpers
  testImplementation("androidx.room:room-testing:${room_version}")


  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  testImplementation("org.mockito.kotlin:mockito-kotlin:6.1.0")
  testImplementation("androidx.arch.core:core-testing:2.2.0")
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
  testImplementation(kotlin("test"))
}