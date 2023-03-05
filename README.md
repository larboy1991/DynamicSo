### 实现简易版的so库动态下发：
- 1、编译期间获取当前app的对应架构下的so库 （这边默认一个，如果有需要自行扩展），判断so库是否发生了变化，包括新增和更新两种，并记录下来
- 2、如果有变化则上传至远端oss服务器等，然后记录当前的so下载地址，处理完成后删除编译时的so文件（注意，这边由于是demo，因此没有实现这一步，而是直接拷贝到assets目录下）
- 3、将记录下来的信息保存到一个json文件里面，会更新到三个位置，分别是
  - `app/src/main/assets/SoInfo.json`
  - `app/build/intermediates/assets/debug/mergeDebugAssets/SoInfo.json`
  - `app/build/intermediates/compressed_assets/debug/out/assets/SoInfo.json.jar`
- 4、使用[droidassist](https://github.com/didi/DroidAssist)替换`System.loadLibrary()`方法，防止当前的so库不存在时出现崩溃
  - 替换`System.loadLibrary()`为`TestSoLoad.safeLoad()`
  - `TestSoLoad.safeLoad()`里面使用[ReLinker](https://github.com/KeepSafe/ReLinker)里面递归加载so的方法：`ReLinker.recursively().loadLibrary(context, libName)`
- 5、启动后，使用Think方案通过反射将指定so库的目录加入到PathClassLoader里DexPathList的nativeLibraryPathElements里面去
- 6、so目录添加完成后，就可以加载so库，执行native的方法了

### **注意：** 
编译前需要先clean一下，因为如果编译时出现增量编译就不会再次生成so库，然后会因为app/build/intermediates/merged_native_libs/debug/out/lib/arm64-v8a 目录下没有so文件导致生成的json文件出错

### 参考了
- [我的 Android 重构之旅：动态下发 SO 库（上）](https://www.jianshu.com/p/260137fdf7c5)
- [货拉拉 Android 动态资源管理系统原理与实践](https://juejin.cn/post/7113703128733581342)
- [Android动态加载so！这一篇就够了！](https://juejin.cn/post/7107958280097366030)