package com.yarokh.vladyslav.ktxtestapplication

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Activity.coroutineExceptionHandler(message: String, func: () -> Unit): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, throwable ->
        CoroutineScope(Dispatchers.Main).launch {
            showToastMessage(message)
            throwable.printStackTrace()
            func.invoke()
        }
    }
}

fun Fragment.coroutineExceptionHandler(message: Int, func: () -> Unit): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, throwable ->
        CoroutineScope(Dispatchers.Main).launch {
            showToastMessage(getString(message))
            throwable.printStackTrace()
            func.invoke()
        }
    }
}

fun Activity.showToastMessage(text: Int) {
    Toast.makeText(this, getString(text), Toast.LENGTH_LONG).show()
}

fun Activity.showToastMessage(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Fragment.showToastMessage(text: Int) {
    Toast.makeText(this.requireContext(), getString(text), Toast.LENGTH_LONG).show()
}

fun Fragment.showToastMessage(text: String) {
    Toast.makeText(this.requireContext(), text, Toast.LENGTH_LONG).show()
}