package com.example.composepractice.store

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.composepractice.MusicSettings
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object MusicSettingsSerializer:Serializer<MusicSettings> {
    override val defaultValue: MusicSettings
        get() = MusicSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): MusicSettings {
        try {
            return MusicSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: MusicSettings, output: OutputStream) {
        t.writeTo(output)
    }
}