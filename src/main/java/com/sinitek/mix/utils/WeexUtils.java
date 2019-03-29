package com.sinitek.mix.utils;
/* * *
 * created by:   cf.yao
 * on:           2019.三月.25   15:38
 * Des:          weex管理工具
 *
 * * */

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sinitek.mix.BuildConfig;
import com.sinitek.mix.modules.BaseWeexModule;
import com.sinitek.mix.utils.__WXImgLoaderAdapter;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.adapter.DefaultWXHttpAdapter;
import com.taobao.weex.common.WXException;

public class WeexUtils {
    private static final String LOG_TAG = "Weex工具类";

    /**
     * 初始化weex，并读取gradle注入的debug服务器信息
     * @param applicationContext    application对象
     * @param config    自定义的配置文件（用于设置各类适配器）
     */
    public static void initWeex(@NonNull Application applicationContext, @NonNull InitConfig config){
        Log.d(LOG_TAG, "读取到当前的BuileType为："+ BuildConfig.BUILD_TYPE);
        if (BuildConfig.BUILD_TYPE.toLowerCase().contains("debug")){
            Log.d(LOG_TAG, "启动Weex引擎的Debug模式。。。");
            String debugAddrUrl = BuildConfig.debugAddrUrl;
            WXEnvironment.sDebugMode = true;
            WXEnvironment.sApplication = applicationContext;
            WXEnvironment.sDebugNetworkEventReporterEnable = true;
            WXEnvironment.sDebugServerConnectable = true;
            WXEnvironment.sRemoteDebugMode = true;
            WXEnvironment.sRemoteDebugProxyUrl = debugAddrUrl;
        }

        Log.d(LOG_TAG, "正在初始化Weex引擎");
        WXSDKEngine.initialize(applicationContext,config);
    }

    /**
     * 使用默认的配置文件初始化weex，注：当您有自定义的题片和网络处理库时，请在初始化时通过与该方法重载的自定义配置文件传入 。
     * @param applicationContext    application对象
     */
    public static void initWeex(@NonNull Application applicationContext){
        initWeex(
                applicationContext
                , new InitConfig
                        .Builder()
                        .setImgAdapter(new __WXImgLoaderAdapter())
                        .setHttpAdapter(new DefaultWXHttpAdapter())
                        //使用okhttp实现的网络请求适配器（未测试！！！）
//                        .setHttpAdapter(new __WXHttpAdapter())
                        .build());
    }



    /**
     * 注册自定义模块
     * @param customModuleName  模块名 {@link com.sinitek.mix.modules.BaseWeexModule#getModuleName()}
     * @param customModule  自定义的模块
     * @return 模块是否注册成功
     * @throws WXException  注册时可能会产生的异常。
     */
    public static boolean registModule(String customModuleName, Class<? extends BaseWeexModule> customModule) throws WXException {
        return WXSDKEngine.registerModule(customModuleName, customModule);
    }

}
