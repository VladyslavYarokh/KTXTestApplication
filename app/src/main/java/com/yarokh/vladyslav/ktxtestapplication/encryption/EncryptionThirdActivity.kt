package com.yarokh.vladyslav.ktxtestapplication.encryption

import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.crypto.tink.*
import com.google.crypto.tink.aead.AeadConfig
import com.yarokh.vladyslav.ktxtestapplication.databinding.ActivityEncryptionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EncryptionThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEncryptionBinding
    val baseStr = "DSheremetov@beeline.kz"
    val str = "vlados the best"
    private var encryptedPart = ""

    @RequiresApi(Build.VERSION_CODES.M)
    lateinit var encryptionHelper: EncryptionHelper

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEncryptionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        encryptionHelper = EncryptionHelper(applicationContext)

        AeadConfig.register()

        binding.apply {
            btnRegkey.setOnClickListener {
                encryptionHelper.generateKey()
                tvResult.text = (!encryptionHelper.isKeyNotContains()).toString()
            }

            btnEncrypt.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    if (etAlias.text.toString().isEmpty()) {
                        Toast.makeText(this@EncryptionThirdActivity, "Empty", Toast.LENGTH_LONG)
                            .show()
                        tvResult.text = ""
                    } else {
                        encryptedPart = Base64.encodeToString(
                            encryptionHelper.encrypt(
                                str, AliasUtils.getAlias(
                                    baseStr
                                )
                            ), Base64.NO_WRAP
                        )
                        Toast.makeText(
                            this@EncryptionThirdActivity,
                            encryptedPart,
                            Toast.LENGTH_LONG
                        ).show()
                        tvResult.text = encryptedPart
                    }
                }
            }

            btnDecrypt.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    tvResult.text = encryptionHelper.decrypt(
                        Base64.decode(
                            encryptedPart,
                            Base64.NO_WRAP
                        ), AliasUtils.getAlias(baseStr)
                    )
                }
            }
        }
    }
}