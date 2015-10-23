# File: Android.mk
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

#APP_ABI := armeabi armeabi-v7a mips

LOCAL_MODULE:= example
LOCAL_SRC_FILES := example_wrap.c example.c shape_wrap.cpp shape.cpp

#support 
#APP_ABI:=armeabi-v7a armeabi mips x86
TARGET_CFLAGS +:= -mhard-float -D_NDK_MATH_NO_SOFTFP=1

#
#APP_ABI:=armeabi-v7a armeabi mips x86
#for c++


LOCAL_CFLAGS:= -frtti

include $(BUILD_SHARED_LIBRARY)
