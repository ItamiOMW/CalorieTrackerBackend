package com.itami.data.database.firebase

import com.itami.utils.Constants

object FirebaseStorageUrl {

    const val BASE_PATH = ""
    const val PROFILE_PICTURES_PATH = "profile-pictures"

    infix fun String.reference(path: String) = "$this$path%2F"

    infix fun String.getDownloadUrl(fileName: String) = run {
        if(this.isEmpty()) {
            return@run "https://firebasestorage.googleapis.com/v0/b/${Constants.FIREBASE_STORAGE_BUCKET}/o/$fileName?alt=media"
        } else {
            return@run "https://firebasestorage.googleapis.com/v0/b/${Constants.FIREBASE_STORAGE_BUCKET}/o/$this$fileName?alt=media"
        }
    }
}