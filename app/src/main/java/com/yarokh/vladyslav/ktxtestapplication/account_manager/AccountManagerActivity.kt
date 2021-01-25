package com.yarokh.vladyslav.ktxtestapplication.account_manager

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yarokh.vladyslav.ktxtestapplication.R
import kotlinx.android.synthetic.main.activity_account_manager.*

/** also, you need next classes: AccountManagerActivity, Authenticator, GenericAccountService
 *  also, add info to the manifest and create xml authenticator description*/

class AccountManagerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_manager)

        createAccount("Vyarokh@beeline.kz", "R56qwj6#", "AUTHTOKEN")
        createAccount("Vyarokh2@beeline.kz", "R56qwj6#2", "AUTHTOKEN2")
        initViews()
    }

    private fun initViews(){
        btn_manger.setOnClickListener {
            val am: AccountManager = AccountManager.get(this@AccountManagerActivity)
            val accounts = am.accounts
            Log.d("accLog", "${accounts.first().name} and pass is ${am.getPassword(accounts.first())}")
        }

        btn_open_acc_manager.setOnClickListener {
            /**For api above Android 5 (min api 23)
             * Opening account intent directly in application*/
//            @RequiresApi(Build.VERSION_CODES.M)
//            val intent = AccountManager.newChooseAccountIntent(null, null, arrayOf("kz.beeline"), null, null, null, null)
            val intent: Intent? = AccountManager.newChooseAccountIntent(null, null, arrayOf("kz.beeline", "com.google"), false, null, null, null, null)
            startActivityForResult(intent, 1)
        }
    }

    /**Create and add a new account to the application storage*/
    private fun createAccount(email: String?, password: String?, authToken: String?) {
        val am: AccountManager = AccountManager.get(this@AccountManagerActivity)
        val account = Account(email, "kz.beeline")
        am.addAccountExplicitly(account, password, null)
        am.setAuthToken(account, "full_access", authToken)
        am.setPassword(account, password)
    }

    /**Managing the account data after selection in account intent dialog*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 -> {
                val am: AccountManager = AccountManager.get(this@AccountManagerActivity)
                /**const String to retrieve account name from Bundle*/
                val account = am.accounts.first { it.name == data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME) }
                val password = am.getPassword(account)
                et_account.setText(account.name, TextView.BufferType.EDITABLE)
                et_password.setText(password, TextView.BufferType.EDITABLE)
                Log.d("accLog", "name is ${account.name} and password is $password")

            }
        }
    }
}