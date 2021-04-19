package com.yarokh.vladyslav.ktxtestapplication.encryption

import android.content.Context
import com.google.crypto.tink.aead.AeadConfig
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class EncryptionThirdActivityTest {

    private var alias = ""
    private var str = ""

    private lateinit var aliasUtils: AliasUtils
    private lateinit var encryptionHelper: EncryptionHelper

    @get:Rule
    var mTempFolder = TemporaryFolder()

    @Mock
    private lateinit var mockContext: Context

    @Before
    fun setUp() {
        alias = "DSheremetov@beeline.kz"
        str = "vlados the best"

        AeadConfig.register()
        openMocks(this)
        `when`(mockContext.filesDir).thenReturn(mTempFolder.newFolder())

        aliasUtils = AliasUtils
        encryptionHelper = EncryptionHelper(mockContext)
    }

    @Test
    fun getAlias() {
        assertEquals(alias.substringBeforeLast("@"), aliasUtils.getAlias(alias))
    }

    @Test
    fun encrypt(){
        val result = encryptionHelper.encrypt(str, aliasUtils.getAlias(alias))
        assertTrue(result.isNotEmpty())
    }


    @Test
    fun decrypt(){
        val encrypted = encryptionHelper.encrypt(str, aliasUtils.getAlias(alias))
        val result = encryptionHelper.decrypt(encrypted, aliasUtils.getAlias(alias))
        assertEquals(result, str)
    }
}