#region fix#
#指定压缩级别
-optimizationpasses 5
#不使用大小写混合类名
-dontusemixedcaseclassnames
#混淆时采用的算法
-dontskipnonpubliclibraryclasses
#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers
#不做预校验操作
-dontpreverify
#生成映射文件，上传bugly，方便定位代码
-verbose
-printmapping proguardMapping.txt
#混淆采用的算法
-optimizations !code/simplification/cast,!field/*,!class/merging/*
#不混淆注解及内部类
-keepattributes *Annotation*,InnerClasses
#不混淆泛型
-keepattributes Signature
#保留行号
-keepattributes SourceFile,LineNumberTable

#忽略警告enableR8=true时会编译不了
#-ignorewarning

-dontwarn com.tencent.bugly.**
-dontwarn com.android.internal.http.multipart.**
-dontwarn org.apache.commons.**
-dontwarn org.apache.http.**
-dontwarn java.lang.invoke.*
-dontwarn javax.lang.model.*
-dontwarn rx.**
-dontwarn org.mozilla.javascript.**
-dontwarn org.mozilla.classfile.**
-dontwarn com.google.gson.**
-dontwarn com.alipay.**

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class * extends android.support.v4.app.Fragment
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


###################################以上为通用###############################################
##########################################################################################
##########################################################################################

#被注解了就不要混淆了
-keepattributes *JavascriptInterface*
-keepattributes *ModuleInterface*

#阿里云
-keep class com.alibaba.sdk.android.oss.** { *; }
-keep class com.yt.utils.photopicker.OssService

# bugly
-keep public class com.tencent.bugly.**{*;}


#ailpay
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.util.H5PayResultModel{*;}
-keep class com.alipay.sdk.app.H5PayCallback {*;}
-keep class com.yt.custom.view.StateLayout$*
-keep class com.yt.custom.view.StateLayout$Action$*
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}
-keep class com.yt.mall.pay.view.PayMethodContainer$*{*;}
-keep class **.Events$*{*;}
-keep class com.yt.utils.photopicker.OssService$*{*;}
-keep class com.yt.widgets.BatchModifyProfitPopuwindow$*{*;}
-keep class com.yt.widgets.ManagePopuWindow$*{*;}
-keep class com.yt.widgets.SharePopuWindow$*{*;}
-keep interface com.yt.mall.home.template.IDividerDecoration


#微信支付
-keep class com.tencent.mm.sdk.** {*;}
-keep class com.tencent.** { *;}
-keep class com.tencent.mm.opensdk.** {*;}

#eventbus
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}

-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#zxing
-keep class com.google.zxing.** {*;}

#Gson
-keep class com.google.gson.** {*;}
-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class * implements java.io.Serializable {
    *;
}

-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
 static final long serialVersionUID;
 private static final java.io.ObjectStreamField[] serialPersistentFields;
 private void writeObject(java.io.ObjectOutputStream);
 private void readObject(java.io.ObjectInputStream);
 java.lang.Object writeReplace();
 java.lang.Object readResolve();
}
#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# 泛型与反射
-keepattributes EnclosingMethod

# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保持枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

#Okhttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase


#okgo
-dontwarn com.lzy.okgo.**
-keep class com.lzy.okgo.**{*;}

#okrx2
-dontwarn com.lzy.okrx2.**
-keep class com.lzy.okrx2.**{*;}

#okserver
-dontwarn com.lzy.okserver.**
-keep class com.lzy.okserver.**{*;}

#Rxjava
-keep class rx.** { *; }

-keep class android.net.compatibility.**{*;}
-keep class android.net.http.**{*;}
-keep class android.internal.http.multipart.**{*;}
-keep class org.apache.commons.**{*;}
-keep class org.apache.http.**{*;}

#Log.X
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** w(...);
    public static *** v(...);
    public static *** i(...);
}

#阿里云
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

#BaseRecyclerViewAdapterHelper
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}

#fast json
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*; }

#防止字段混淆
-keepclassmembers class * {
    @com.alibaba.fastjson.annotation.JSONField <methods>;
}

#ARouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

#UCop 裁剪图片
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }
