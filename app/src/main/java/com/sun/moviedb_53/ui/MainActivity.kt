package com.sun.moviedb_53.ui

import com.sun.moviedb_53.R
import com.sun.moviedb_53.base.BaseActivity
import com.sun.moviedb_53.ui.homepage.HomePageFragment

class MainActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_main

    override fun onEvent() {
    }

    override fun onInit() {
        supportFragmentManager.beginTransaction()
            .add(R.id.mFrameMain, HomePageFragment()).commit()
    }
}
