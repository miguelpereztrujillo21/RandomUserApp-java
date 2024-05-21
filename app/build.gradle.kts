
plugins {
    alias(libs.plugins.androidApplication)

    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.mpt.randomuserapp_java"
    compileSdk = 34
    dataBinding.enable = true

    defaultConfig {
        applicationId = "com.mpt.randomuserapp_java"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //LiveData + ViewModel


    //Retrofit + Gson
    implementation(libs.retrofit)
    implementation(libs.retrofitConverterGson)
    //Dagger Hilt
    implementation(libs.dagger)
    annotationProcessor(libs.daggerCompiler)



}


