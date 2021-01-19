package com.yarokh.vladyslav.ktxtestapplication

import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_encryption.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec


class EncryptionActivity : AppCompatActivity() {
    private val baseStr = "DSheremetov@beeline.kz"
    private val str = "My name is Vladyslav Yarokh and I'm android developer"
    private val ANDROID_KEY_STORE = "AndroidKeyStore"
    private val KEY_ALGORITHM = "AES/GCM/NoPadding"
    private val ALIAS_ERROR = "Alias is incorrect"
    private lateinit var iv: ByteArray
    private var keyStore: KeyStore = KeyStore.getInstance(ANDROID_KEY_STORE)

    init {
        keyStore.load(null)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encryption)

        val encryptedPart = encrypt(str, baseStr.substringBeforeLast("@"))

        btnDecrypt.setOnClickListener {
            CoroutineScope(Dispatchers.Main + coroutineExceptionHandler(ALIAS_ERROR) {
                etAlias.text.clear()
            }).launch {
                if (etAlias.text.toString().isEmpty()) {
                    Toast.makeText(this@EncryptionActivity, "Empty", Toast.LENGTH_LONG).show()
                } else {
                    decrypt(encryptedPart, etAlias.text.toString())
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun encrypt(msg: String, alias: String): ByteArray {
        val cipher = Cipher.getInstance(KEY_ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey(alias))
        iv = cipher.iv
        val encryptedByteArray = cipher.doFinal(msg.toByteArray(Charsets.UTF_8))
        return Base64.encode(encryptedByteArray, Base64.NO_WRAP)
    }

    private fun decrypt(encryptedText: ByteArray, alias: String): String {
        val spec = GCMParameterSpec(128, iv)
        val secretKeyEntry = keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry
        val key = secretKeyEntry.secretKey
        val decryptedByteArray = Base64.decode(encryptedText, Base64.NO_WRAP)
        val cipher = Cipher.getInstance(KEY_ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key, spec)
        return String(cipher.doFinal(decryptedByteArray), Charsets.UTF_8)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun generateSecretKey(alias: String): SecretKey {
        val keyGenerator: KeyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEY_STORE
        )
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        return keyGenerator.generateKey()
    }
}