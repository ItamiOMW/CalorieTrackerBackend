package com.itami.di

import com.google.cloud.storage.Bucket
import com.google.firebase.cloud.StorageClient
import org.koin.dsl.module

val mainModule = module {
    single<Bucket> { StorageClient.getInstance().bucket() }
}

