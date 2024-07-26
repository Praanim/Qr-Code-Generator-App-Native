plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.qrgeneratorscanner"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.qrgeneratorscanner"
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

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("com.google.zxing:core:3.4.1")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

//    //camera dependencies
//    implementation("androidx.camera:camera-core:1.1.0-alpha04");
//    implementation ("androidx.camera:camera-camera2:1.1.0-alpha04");
//    implementation("androidx.camera:camera-lifecycle:1.1.0-alpha04") ;
//    implementation ("androidx.camera:camera-view:1.0.0-alpha24");
}