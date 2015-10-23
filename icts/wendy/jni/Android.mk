LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Wendy_Android
LOCAL_SRC_FILES := Wendy_Android.cpp

include $(BUILD_SHARED_LIBRARY)
