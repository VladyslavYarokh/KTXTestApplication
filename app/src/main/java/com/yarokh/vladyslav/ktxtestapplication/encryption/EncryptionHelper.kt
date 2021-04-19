package com.yarokh.vladyslav.ktxtestapplication.encryption

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.crypto.tink.*
import com.google.crypto.tink.aead.AesGcmKeyManager
import java.io.File


@RequiresApi(Build.VERSION_CODES.M)
class EncryptionHelper(private val context: Context) {

    fun encrypt(msg: String, alias: String): ByteArray {
        generateKey()
        val keysetFilename = context.filesDir.path + "/my_keyset.json"
        val keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withFile(File(keysetFilename)))
        val encryptor = keysetHandle.getPrimitive(Aead::class.java)
        val encrypted = encryptor.encrypt(
            msg.toByteArray(Charsets.ISO_8859_1), alias.toByteArray(
                Charsets.ISO_8859_1
            )
        )
        println(encrypted)
        return encrypted ?: error("encrypted is $encrypted, but error in Base64")
    }

    fun decrypt(encryptedText: ByteArray, alias: String): String {
        generateKey()
        val keysetFilename = context.filesDir.path + "/my_keyset.json"
        val keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withFile(File(keysetFilename)))
        val decryptor = keysetHandle.getPrimitive(Aead::class.java)
        val decrypted = decryptor.decrypt(encryptedText, alias.toByteArray(Charsets.ISO_8859_1))
        return String(decrypted, Charsets.ISO_8859_1)
    }

    fun generateKey(){
        if(isKeyNotContains()) {
            val keysetHandle = KeysetHandle.generateNew(AesGcmKeyManager.aes128GcmTemplate())
            val keysetFilename = context.filesDir.path + "/my_keyset.json"
            CleartextKeysetHandle.write(
                keysetHandle,
                JsonKeysetWriter.withFile(File(keysetFilename))
            )
        }
    }

    fun isKeyNotContains(): Boolean {
        val f = File(context.filesDir.path + "/")
        val files = f.list { _, name -> name == "my_keyset.json" }
        return files!!.isEmpty()
    }
}