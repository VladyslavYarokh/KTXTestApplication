package com.yarokh.vladyslav.ktxtestapplication.account_manager

import android.app.Service
import android.content.Intent

import android.os.IBinder


class GenericAccountService : Service() {
    private var mAuthenticator: Authenticator? = null
    override fun onCreate() {
        super.onCreate()
        synchronized(lock) {
            if (mAuthenticator == null) {
                mAuthenticator =
                    Authenticator(
                        this
                    )
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return mAuthenticator!!.iBinder
    }

    companion object {
        private val lock = Any()
    }
}