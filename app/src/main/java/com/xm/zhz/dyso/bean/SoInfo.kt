package com.xm.zhz.dyso.bean


data class SoInfo(val armeabi: String, val time: Long, val list: List<SoInfoItem>)


data class SoInfoItem(val name: String, val md5: String, val version: Long, val size: Long, val url: String)