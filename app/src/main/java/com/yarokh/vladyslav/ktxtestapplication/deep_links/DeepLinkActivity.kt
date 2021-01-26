package com.yarokh.vladyslav.ktxtestapplication.deep_links

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yarokh.vladyslav.ktxtestapplication.R

class DeepLinkActivity: AppCompatActivity() {

    /**look at the manifest to define how it works
     * use internal App Links Assistant to create
     * a new deep links for each activity
     * look for more information:
     * https://developer.android.com/training/app-links/deep-linking
     * https://developer.android.com/guide/navigation/navigation-deep-link
     * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link_unselected)
    }
}