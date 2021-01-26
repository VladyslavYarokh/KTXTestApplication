package com.yarokh.vladyslav.ktxtestapplication.deep_links

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yarokh.vladyslav.ktxtestapplication.R
import kotlinx.android.synthetic.main.activity_deep_link_selected.*

class DeepLinkSelectedActivity: AppCompatActivity() {

    /**look at the manifest to define how it works
     * use internal App Links Assistant to create
     * a new deep links for each activity
     * look for more information:
     * https://developer.android.com/training/app-links/deep-linking
     * https://developer.android.com/guide/navigation/navigation-deep-link
     *
     * https://vladyslav.yarokh.com/?id=6&place=Planet
     *
     * correct example to pass the data from outside the app above this line*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link_selected)

        val data = intent?.data
        tv_number.text = data?.getQueryParameter("id")
        tv_place.text = data?.getQueryParameter("place")
    }
}