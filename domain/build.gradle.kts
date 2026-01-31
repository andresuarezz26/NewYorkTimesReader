plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.serialization)
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.newyorktimesreader.domain"
  compileSdk = 36

  defaultConfig {
    minSdk = 23

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  // Hilt
  implementation(libs.dagger.hilt.android)
  ksp(libs.dagger.hilt.android.compiler)

  // Coroutines
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

  // Serialization
  implementation(libs.kotlinx.serialization.json)

  // Testing
  testImplementation(libs.androidx.core.testing)
  testImplementation(kotlin("test"))
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
  // Mockito
  testImplementation(libs.mockito.core)
  testImplementation(libs.mockito.kotlin)
  androidTestImplementation(libs.mockito.android)
}