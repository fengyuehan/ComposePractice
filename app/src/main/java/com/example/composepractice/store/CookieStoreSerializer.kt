package com.example.composepractice.store

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.composepractice.CookieStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object CookieStoreSerializer :Serializer<CookieStore>{
    override val defaultValue: CookieStore
        get() = CookieStore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CookieStore {
        try {
            return CookieStore.parseFrom(input)
        }catch (exception:InvalidProtocolBufferException){
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: CookieStore, output: OutputStream) {
        t.writeTo(output
        )
    }
}