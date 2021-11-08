package com.example.composepractice.store

import androidx.datastore.core.Serializer
import com.google.protobuf.Any.getDefaultInstance
import java.io.InputStream
import java.io.OutputStream
import java.net.CookieStore

object CookieStoreSerializer :Serializer<CookieStore>{
    override val defaultValue: CookieStore
        get() = CookieStore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CookieStore {
        TODO("Not yet implemented")
    }

    override suspend fun writeTo(t: CookieStore, output: OutputStream) {
        TODO("Not yet implemented")
    }
}