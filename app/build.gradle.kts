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

  // Compose
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.compose.runtime.livedata)
  androidTestImplementation(libs.androidx.compose.ui.test.junit)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))

  // Coil
  implementation(libs.coil.compose)
  implementation(libs.coil.network)

  // Serialization
  implementation(libs.kotlinx.serialization.json)

  // Hilt
  implementation(libs.dagger.hilt.android)
  ksp(libs.dagger.hilt.android.compiler)
  implementation(libs.hilt.navigation.compose)

  // RxJava
  implementation(libs.rx.java)
  implementation(libs.rx.kotlin)
  implementation(libs.rx.android)

  // Mockito
  testImplementation(libs.mockito.core)
  testImplementation(libs.mockito.kotlin)
  androidTestImplementation(libs.mockito.android)

  // Android ktx
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)

  // Android UI
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)

  // Retrofit
  implementation(libs.retrofit)
  implementation(libs.retrofit.converter.gson)
  implementation(libs.retrofit.adapter.rxjava3)

  // Logging interceptor
  implementation(libs.httpLoggingInterceptor)

  // Room
  implementation(libs.room.runtime)
  ksp(libs.room.compiler)
  implementation(libs.room.rx.java3)
  testImplementation(libs.room.testing)

  // General Test
  testImplementation(libs.androidx.core.testing)
  testImplementation(kotlin("test"))
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)

  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

}