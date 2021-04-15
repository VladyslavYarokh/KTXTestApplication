package com.yarokh.vladyslav.ktxtestapplication.encryption

import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.yarokh.vladyslav.ktxtestapplication.R
import com.yarokh.vladyslav.ktxtestapplication.coroutineExceptionHandler
import kotlinx.android.synthetic.main.activity_encryption.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.and
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
    private var keyStore: KeyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
    private var encryptedPart = ""
    private var SEPARATOR = "SEPARATOR"

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
                    Log.d("loggs", encryptedPart)
                }
            }
        }

        btnDecrypt.setOnClickListener {
            CoroutineScope(Dispatchers.Main + coroutineExceptionHandler(ALIAS_ERROR) {
                etAlias.text.clear()
            }).launch {
                Toast.makeText(this@EncryptionSecondActivity, decrypt(encryptedPart, baseStr), Toast.LENGTH_LONG).show()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun encrypt(msg: String, alias: String): String {
        val cipher = Cipher.getInstance(KEY_ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey(alias))
        val iv = cipher.iv
        val encrypted = cipher.doFinal(msg.toByteArray(Charsets.ISO_8859_1))
        return byteArrayToString(encrypted) + SEPARATOR + byteArrayToString(iv)
    }

    private fun decrypt(encryptedText: String, alias: String): String {
        val parts = encryptedText.split(SEPARATOR)
        val decrypted = stringToByteArray(parts[0])
        val iv = stringToByteArray(parts[1])
        val spec = GCMParameterSpec(128, iv)
        val secretKeyEntry = keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry
        val key = secretKeyEntry.secretKey
        val cipher = Cipher.getInstance(KEY_ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key, spec)
        return String(cipher.doFinal(decrypted), Charsets.ISO_8859_1)
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


    private fun byteArrayToString(bytes: ByteArray): String? {
        val sb = StringBuilder()
        for (b: Byte in bytes) {
            sb.append(String.format("%02x", b and 0xff))
        }
        return sb.toString()
    }

    private fun stringToByteArray(s: String?): ByteArray? {
        if (s == null) {
            return byteArrayOf()
        }
        if (s.length % 2 != 0 || s.isEmpty()) {
            return byteArrayOf()
        }
        val data = ByteArray(s.length / 2)
        var i = 0
        while (i < s.length) {
            try {
                data[i / 2] =
                    Integer.decode("0x" + s[i] + s[i + 1]).toByte()
            } catch (e: NumberFormatException) {
                return byteArrayOf()
            }
            i += 2
        }
        return data
    }
}