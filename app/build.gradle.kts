plugins {
  id("com.android.application")
  id("kotlin-android")
  id("dagger.hilt.android.plugin")
  id("com.google.devtools.ksp")
}

android {
  namespace = "cyou.ma.bartender"
  compileSdk = 34

  defaultConfig {
    applicationId = "cyou.ma.bartender"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    ksp {
      arg("room.schemaLocation", "$projectDir/schemas")
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }

  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = rootProject.extra["composeVersion"] as String
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xallow-result-return-type")
  }
}

dependencies {
  val kotlinVersion = rootProject.extra["kotlinVersion"] as String

  implementation("androidx.core:core-ktx:1.12.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.10.0")
  implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

  // Compose
  val composeVersion = rootProject.extra["composeVersion"] as String
  implementation(platform("androidx.compose:compose-bom:2023.10.01"))

  debugImplementation("androidx.compose.ui:ui-tooling")
  implementation("androidx.compose.ui:ui-tooling-preview")

  implementation("androidx.compose.material:material")
  implementation("androidx.compose.foundation:foundation")
  implementation("androidx.compose.ui:ui")

  implementation("androidx.compose.material:material-icons-core")
  implementation("androidx.compose.material:material-icons-extended")

  // Activity
  implementation("androidx.activity:activity-ktx:1.8.1")

  implementation("androidx.activity:activity-compose:1.8.1")

  //lifecycle
  implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.2")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

  // Hilt
  val hiltVersion = rootProject.extra["hiltVersion"] as String
  implementation("com.google.dagger:hilt-android:$hiltVersion")
  ksp("com.google.dagger:hilt-android-compiler:$hiltVersion")

  implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

  // Other Compose Integrations
  implementation("androidx.compose.runtime:runtime-livedata:1.5.4")
  implementation("androidx.navigation:navigation-compose:2.7.5")

  // Accompanist
  implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")
  implementation("com.google.accompanist:accompanist-coil:0.15.0")

  // Room
  implementation("androidx.room:room-runtime:2.6.0")
  ksp("androidx.room:room-compiler:2.6.0")
  implementation("androidx.room:room-ktx:2.6.0")

  // Retrofit
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
  implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

  // Moshi
  implementation("com.squareup.moshi:moshi:1.15.0")
  ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

  implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")


}
