package com.yarokh.vladyslav.ktxtestapplication.encryption

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.crypto.tink.*
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.yarokh.vladyslav.ktxtestapplication.R
import kotlinx.android.synthetic.main.activity_encryption.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.and
import java.io.File


class EncryptionThirdActivity : AppCompatActivity() {
    private val baseStr = "DSheremetov@beeline.kz"
    private val str = "AQBfaTsCP3HwC1xquiB1Hw7dwPGnxtkXvIB3HMYjjtYRYK+9Eg=="
    private val ANDROID_KEY_STORE = "AndroidKeyStore"
    private val KEY_ALGORITHM = "AES/GCM/NoPadding"
    private val ALIAS_ERROR = "Alias is incorrect"
    private var encryptedPart = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encryption)

        AeadConfig.register()

        btnRegkey.setOnClickListener {
            generateKey()
        }
//        val manager = AndroidKeysetManager.Builder().withSharedPref(
//            applicationContext,
//            baseStr.substringBeforeLast("@"),
//            baseStr.substringBeforeLast("@")
//        )
//            .withKeyTemplate(AesGcmKeyManager.aes128GcmTemplate())
//            .withMasterKeyUri(masterKeyUri)
//            .build()

        btnEncrypt.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                if (etAlias.text.toString().isEmpty()) {
                    Toast.makeText(this@EncryptionThirdActivity, "Empty", Toast.LENGTH_LONG).show()
                } else {
                    encryptedPart = encrypt(etAlias.text.toString(), baseStr)
                    Toast.makeText(this@EncryptionThirdActivity, encryptedPart, Toast.LENGTH_LONG).show()
                    Log.d("loggs", encryptedPart)
                }
            }
        }

        btnDecrypt.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(this@EncryptionThirdActivity, decrypt(str, baseStr), Toast.LENGTH_LONG).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun encrypt(msg: String, alias: String): String {
//        val keysetFilename = filesDir.path.toString() + "/my_keyset.json"
        val keysetFilename = applicationContext.filesDir.path + "/my_keyset.json"
        val keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withFile(File(keysetFilename)))
        val encryptor = keysetHandle.getPrimitive(Aead::class.java)
        val encrypted = encryptor.encrypt(msg.toByteArray(Charsets.ISO_8859_1), alias.toByteArray(Charsets.ISO_8859_1))
        return Base64.encodeToString(encrypted, Base64.NO_WRAP) ?: error("NullPointerException")
    }

    private fun decrypt(encryptedText: String, alias: String): String {
//        val keysetFilename = filesDir.path.toString() + "/my_keyset.json"
        val keysetFilename = applicationContext.filesDir.path + "/my_keyset.json"
        val keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withFile(File(keysetFilename)))
        val decryptor = keysetHandle.getPrimitive(Aead::class.java)
        val decrypted = decryptor.decrypt(Base64.decode(encryptedText, Base64.NO_WRAP), alias.toByteArray(Charsets.ISO_8859_1))
        return String(decrypted, Charsets.ISO_8859_1)
    }

    private fun generateKey(){
//        val f = File(filesDir.path.toString() + "/")
        val f = File(applicationContext.filesDir.path + "/")
        val files = f.list { _, name -> name == "my_keyset.json" }
        if(files!!.isEmpty()) {
            val keysetHandle = KeysetHandle.generateNew(AesGcmKeyManager.aes128GcmTemplate())
//            val keysetFilename = filesDir.path.toString() + "/my_keyset.json"
            val keysetFilename = applicationContext.filesDir.path + "/my_keyset.json"
            CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(File(keysetFilename)))
        }
    }
}