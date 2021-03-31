package com.yarokh.vladyslav.ktxtestapplication

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
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

@SuppressLint("ClickableViewAccessibility")
private fun TextView.touchListener(lines: Int): View.OnTouchListener {
    return View.OnTouchListener { _, event ->
        if (event.rawX >= (this.right - this.compoundDrawables[2].bounds.width())) {
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (this.maxLines > 1) {
                    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand, 0)
                    maxLines = 1
                    return@OnTouchListener true
                } else {
                    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_compress, 0)
                    this.maxLines = lines
                    return@OnTouchListener true
                }
            }
        }
        return@OnTouchListener false
    }
}

@SuppressLint("ClickableViewAccessibility")
fun TextView.setOnShowFieldListener(lines: Int){
    this.setOnTouchListener(this.touchListener(lines))
}

@SuppressLint("ClickableViewAccessibility")
private fun EditText.touchListener(lines: Int): View.OnTouchListener {
    return View.OnTouchListener { _, event ->
        if (event.rawX >= (this.right - this.compoundDrawables[2].bounds.width())) {
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (this.maxLines > 1) {
                    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand, 0)
                    maxLines = 1
                    return@OnTouchListener true
                } else {
                    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_compress, 0)
                    this.maxLines = lines
                    return@OnTouchListener true
                }
            }
        }
        return@OnTouchListener false
    }
}

@SuppressLint("ClickableViewAccessibility")
fun EditText.setOnShowFieldListener(lines: Int){
    this.setOnTouchListener(this.touchListener(lines))
}