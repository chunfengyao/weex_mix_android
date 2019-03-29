#官方的混淆规则开始
-keep class com.taobao.weex.bridge.** { *; }
-keep class com.taobao.weex.layout.** { *; }
-keep class com.taobao.weex.WXSDKEngine { *; }
-keep class com.taobao.weex.base.SystemMessageHandler { *; }
-dontwarn com.taobao.weex.bridge.**
#官方的混淆规则结束