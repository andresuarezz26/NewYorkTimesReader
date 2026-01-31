import java.util.Properties

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  id("com.google.devtools.ksp")
  id("kotlin-parcelize")
}

val props = Properties()
val propFile = rootProject.file("local.properties")

if (propFile.exists()) {
  // Load the file contents into the 'props' variable
  propFile.inputStream().use { props.load(it) }
}

android {
  namespace = "com.newyorktimesreader.data"
  compileSdk = 36

  buildFeatures {
    buildConfig = true
  }



  defaultConfig {
    minSdk = 23

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
    buildConfigField(
      "String",
      "NYT_API_KEY",
      "\"${props.getProperty("NYT_API_KEY", "DEFAULT_FALLBACK_KEY")}\"")
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

  implementation(project(":domain"))

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  // Hilt
  implementation(libs.dagger.hilt.android)
  ksp(libs.dagger.hilt.android.compiler)

  // Retrofit
  implementation(libs.retrofit)
  implementation(libs.retrofit.converter.gson)

  // Logging interceptor
  implementation(libs.httpLoggingInterceptor)

  // Room
  implementation(libs.room.runtime)
  ksp(libs.room.compiler)
  testImplementation(libs.room.testing)


  // Testing
  testImplementation(libs.androidx.core.testing)
  testImplementation(kotlin("test"))
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
  // Mockito
  testImplementation(libs.mockito.core)
  testImplementation(libs.mockito.kotlin)
  androidTestImplementation(libs.mockito.android)

}