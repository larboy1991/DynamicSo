package com.xm.zhz.dyso

class JNITest {

    companion object {
        init {
            // 当前这个方法会在编译期间换成TestSoLoad.safeLoad(),防止因为没有so库导致报错
            System.loadLibrary("dyso")
        }
    }

    external fun stringFromJNI(): String

}