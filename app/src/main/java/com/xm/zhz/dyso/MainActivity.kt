package com.xm.zhz.dyso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.gson.Gson
import com.xm.zhz.dyso.bean.SoInfo
import java.io.File

class MainActivity : AppCompatActivity() {

    private val soInfo by lazy { parseSoJson() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.mBtn1)
        val btn2 = findViewById<Button>(R.id.mBtn2)
        // 解析对应文件进行下的md5和版本号是否一致，这边假设一致，那么接下来就是如何去加载so库实现了
        btn.setOnClickListener {
            AssetsUtils.copyAssetFileToInternalStorage(this)
            val path = "${filesDir.absolutePath}/arm64-v8a"
            //1、 将当前目录下的so库都加入到classLoader里面去
            TestSoLoad.insertPathToNativeSystem(this, File(path))
            //2、加载完成后，遍历load当前所有的so库
            soInfo.list.forEach {
                TestSoLoad.safeLoad(it.name)
            }
            Toast.makeText(this, "所有的So库都加载完成了，可以点击执行native的方法了", Toast.LENGTH_SHORT).show()
        }

        btn2.setOnClickListener {
            Toast.makeText(this, "执行了native的方法：${JNITest().stringFromJNI()}", Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * 解析assets的soInfo.json
     * <p>
     * Date: 2023-03-04
     * Author: zhuanghongzhan
     */
    private fun parseSoJson(): SoInfo {
        return readJsonFromAssets("SoInfo.json")
    }

    private fun readJsonFromAssets(fileName: String): SoInfo {
        val jsonString = assets.open(fileName).bufferedReader().use { it.readText() }
        return Gson().fromJson(jsonString, SoInfo::class.java)
    }

}