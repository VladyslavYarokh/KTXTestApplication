package com.yarokh.vladyslav.ktxtestapplication

import android.accounts.*
import android.content.Context
import android.content.Intent
import android.os.Bundle


class Authenticator(private val context: Context) : AbstractAccountAuthenticator(context) {
    override fun getAuthTokenLabel(authTokenType: String?): String {
        return authTokenType!!
    }

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        options: Bundle?
    ): Bundle {
        return options!!
    }

    override fun updateCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {
        return options!!
    }

    override fun getAuthToken(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {
        return options!!
    }

    override fun hasFeatures(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        features: Array<out String>?
    ): Bundle {
        return Bundle()
    }

    override fun editProperties(
        response: AccountAuthenticatorResponse?,
        accountType: String?
    ): Bundle {
        return Bundle()
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse,
        accountType: String,
        authTokenType: String,
        requiredFeatures: Array<String>,
        options: Bundle
    ): Bundle {
        val intent = Intent(context, AccountManagerActivity::class.java)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }
}