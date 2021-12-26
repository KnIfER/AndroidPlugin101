#include "com_knziha_myapplication_exampleact_MainActivity.h"
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

extern "C" {
jstring Java_com_knziha_myapplication_exampleact_MainActivity_getString(JNIEnv* env, jobject)
{
    return env->NewStringUTF("馄饨好吃!!!");
}
}

