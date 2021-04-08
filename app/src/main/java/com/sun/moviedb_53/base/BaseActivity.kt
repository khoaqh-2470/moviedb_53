package com.sun.moviedb_53.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        onInit()
        onEvent()
    }

    abstract fun getLayoutId(): Int

    abstract fun onEvent()

    abstract fun onInit()
}
