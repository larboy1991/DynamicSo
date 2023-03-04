package com.xm.zhz.dyso

import android.content.Context
import com.getkeepsafe.relinker.ReLinker
import com.xm.zhz.dyso.utils.tinker.TinkerLoadLibrary
import java.io.File

object TestSoLoad {

    /**
     * 以安全的方式加载so
     * <p>
     * Date: 2023-03-04
     * Author: zhuanghongzhan
     */
    fun safeLoad(libName: String) {
        val context = MyApplication.getInstance()
        try {
            ReLinker.recursively().loadLibrary(context, libName)
            // TODO 将当前的lib从带加载的列表里面移除
        } catch (e: Exception) {
            e.printStackTrace()
            // TODO 如果出现异常则将当前的lib加入到带加载的列表里面去
        }
    }

    fun insertPathToNativeSystem(context: Context, file: File) {
        try {
            TinkerLoadLibrary.installNativeLibraryPath(context.classLoader, file)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

}