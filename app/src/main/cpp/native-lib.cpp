#include <jni.h>
#include <string>

//com.xm.zhz.dyso com.xm.zhz.dyso
extern "C" JNIEXPORT jstring JNICALL
Java_com_xm_zhz_dyso_JNITest_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}