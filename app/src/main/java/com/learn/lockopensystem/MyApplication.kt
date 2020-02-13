package com.learn.lockopensystem

import com.facebook.drawee.backends.pipeline.Fresco
import android.app.Application


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}

