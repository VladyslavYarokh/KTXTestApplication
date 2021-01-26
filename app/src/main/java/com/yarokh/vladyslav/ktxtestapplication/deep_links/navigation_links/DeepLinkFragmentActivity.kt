package com.yarokh.vladyslav.ktxtestapplication.deep_links.navigation_links

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.yarokh.vladyslav.ktxtestapplication.R


class DeepLinkFragmentActivity : AppCompatActivity() {

    /**
     * create deep link in main_graph using +Deep Link
     * specify the additional data in {} like /{id}?place={place}
     * add arguments with the same name and default values to fragment in main graph
     * use navArgs to retrieve data inside fragment
     * add activity to manifest and specify <nav-graph> param inside activity tags
     * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link_navigation)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}