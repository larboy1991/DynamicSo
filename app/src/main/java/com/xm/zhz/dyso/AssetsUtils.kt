package com.xm.zhz.dyso

import android.content.Context
import java.io.File
import java.io.FileOutputStream

object AssetsUtils {


    fun copyAssetFileToInternalStorage(context: Context) {
        // 获取 assets 文件夹中的所有文件和文件夹
        val assetsManager = context.assets
        val assetsList = assetsManager.list("") ?: return
        for (assetPath in assetsList) {
            // 判断是否为文件夹
            val isDirectory = assetsManager.list(assetPath)?.isNotEmpty() ?: false
            if (isDirectory) {
                // 如果是文件夹，递归调用复制方法
                copyAssetsFolder(context, assetPath)
            } else {
                // 如果是文件，复制到 filesDir 文件夹下
                val inputStream = assetsManager.open(assetPath)
                val outputStream = FileOutputStream(File(context.filesDir, assetPath))
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
            }
        }
    }

    // 复制文件夹到 filesDir 文件夹下
    private fun copyAssetsFolder(context: Context, folderPath: String) {
        // 创建目标文件夹
        val folder = File(context.filesDir, folderPath)
        folder.mkdirs()

        // 获取文件夹内所有文件和文件夹
        val assetsManager = context.assets
        val assetsList = assetsManager.list(folderPath) ?: return
        for (assetPath in assetsList) {
            // 构建完整路径
            val fullPath = "$folderPath/$assetPath"
            // 判断是否为文件夹
            val isDirectory = assetsManager.list(fullPath)?.isNotEmpty() ?: false
            if (isDirectory) {
                // 如果是文件夹，递归调用复制方法
                copyAssetsFolder(context, fullPath)
            } else {
                // 如果是文件，复制到目标文件夹下
                val inputStream = assetsManager.open(fullPath)
                val outputStream = FileOutputStream(File(folder, assetPath))
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
            }
        }
    }

}