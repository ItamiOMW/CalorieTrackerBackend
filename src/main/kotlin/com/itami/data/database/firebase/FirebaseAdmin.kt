package com.itami.data.database.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.itami.utils.Constants
import java.io.InputStream

object FirebaseAdmin {

//    private val serviceAccount: InputStream? =
//        this::class.java.classLoader.getResourceAsStream("itami-chat-images-firebase-adminsdk-x1l8u-e6f032d302.json")

    private val firebaseAdminSdk: InputStream = System.getenv("FIREBASE_ADMIN_SDK").byteInputStream()


    private val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(firebaseAdminSdk))
        .setStorageBucket(Constants.FIREBASE_STORAGE_BUCKET)
        .build()

    fun init(): FirebaseApp = FirebaseApp.initializeApp(options)

}