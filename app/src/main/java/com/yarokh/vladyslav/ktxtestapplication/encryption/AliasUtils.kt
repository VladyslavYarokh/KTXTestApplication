package com.yarokh.vladyslav.ktxtestapplication.encryption

object AliasUtils {
    fun getAlias(alias: String): String {
        return alias.substringBeforeLast("@")
    }
}