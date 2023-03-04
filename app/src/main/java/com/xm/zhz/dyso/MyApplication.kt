package com.xm.zhz.dyso

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    companion object {

        private var mInstance: MyApplication? = null
        fun getInstance(): MyApplication {
            return mInstance!!
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        mInstance = this
    }
}