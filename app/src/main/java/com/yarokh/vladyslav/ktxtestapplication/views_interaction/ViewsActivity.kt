package com.yarokh.vladyslav.ktxtestapplication.views_interaction

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yarokh.vladyslav.ktxtestapplication.R
import com.yarokh.vladyslav.ktxtestapplication.setOnShowFieldListener

class ViewsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_views)

        initViews()
    }

    private fun initViews(){
        val view = findViewById<TextView>(R.id.tv_placeholder)
        view.setOnShowFieldListener(view.maxLines)

        val etView = findViewById<EditText>(R.id.et_placeholder)
        etView.setOnShowFieldListener(view.maxLines)


    }
}