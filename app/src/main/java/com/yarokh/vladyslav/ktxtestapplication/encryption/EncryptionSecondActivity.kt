package com.yarokh.vladyslav.ktxtestapplication.encryption

import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.yarokh.vladyslav.ktxtestapplication.R
import com.yarokh.vladyslav.ktxtestapplication.coroutineExceptionHandler
import kotlinx.android.synthetic.main.activity_encryption.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class EncryptionSecondActivity : AppCompatActivity() {
    private val baseStr = "DSheremetov@beeline.kz"
    private val str = "My name is Vladyslav Yarokh and I'm android developer"
    private val ANDROID_KEY_STORE = "AndroidKeyStore"
    private val KEY_ALGORITHM = "AES/GCM/NoPadding"
    private val ALIAS_ERROR = "Alias is incorrect"
    private var iv = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    private var keyStore: KeyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
    private var encryptedPart = ""

    init {
        keyStore.load(null)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encryption)

        btnEncrypt.setOnClickListener {
            CoroutineScope(Dispatchers.Main + coroutineExceptionHandler(ALIAS_ERROR) {
                etAlias.text.clear()
            }).launch {
                if (etAlias.text.toString().isEmpty()) {
                    Toast.makeText(this@EncryptionSecondActivity, "Empty", Toast.LENGTH_LONG).show()
                } else {
                    encryptedPart = encrypt(etAlias.text.toString(), baseStr)
                }
            }
        }

        btnDecrypt.setOnClickListener {
            CoroutineScope(Dispatchers.Main + coroutineExceptionHandler(ALIAS_ERROR) {
                etAlias.text.clear()
            }).launch {
                Toast.makeText(this@EncryptionSecondActivity, decrypt(encryptedPart.toByteArray(Charsets.ISO_8859_1), baseStr), Toast.LENGTH_LONG).show()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun encrypt(msg: String, alias: String): String {
        val cipher = Cipher.getInstance(KEY_ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey(alias))
        iv = cipher.iv
        return String(cipher.doFinal(msg.toByteArray(Charsets.ISO_8859_1)), Charsets.ISO_8859_1)
    }

    private fun decrypt(encryptedText: ByteArray, alias: String): String {
        val spec = GCMParameterSpec(128, iv)
        val secretKeyEntry = keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry
        val key = secretKeyEntry.secretKey
        val cipher = Cipher.getInstance(KEY_ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key, spec)
        return String(cipher.doFinal(encryptedText), Charsets.ISO_8859_1)
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