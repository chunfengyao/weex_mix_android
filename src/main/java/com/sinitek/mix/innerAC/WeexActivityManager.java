package com.sinitek.mix.innerAC;
/* * *
 * created by:   cf.yao
 * on:           2019.三月.26   10:31
 * Des:          管理Weex视图的管理类，屏蔽外部的直接管理，降低外部项目对该模块中weex相关类的耦合度。
 *
 * * */

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;

import java.io.Serializable;
import java.util.Map;

public class WeexActivityManager implements IWXRenderListener, Serializable {
    private static final String LOG_TAG = "Weex管理类";
    private static final long serialVersionUID = 1L;
    private static WeexActivityManager mInstance = new WeexActivityManager();
    private Map<String, Object> options;
    private IWXRenderListener activityCallBakc;
    private String jsonInitData;
    private WXRenderStrategy renderStrategy;
    //0 webUrl  1 localFile
    private int loadByWebUrl = -1;


    /**
     *  打开指定的服务器端存储的Weex页面
     *  样例代码：
     WeexActivityManager
         .startWeexActivityByWebWeexUrl(
                 mContext
                 , "http://127.0.0.1:8080/shared//assets/examples.weex.js"
                 , "test"
                 , null
                 , null);
     *
     *
     * @param context   用于startActivity的Activity上下文，例如：MainActivity.this
     * @param bundleUrl 在线的js bundle文件的地址
     * @param pageName  当前页面的名称，用于调试信息的定位
     * @param options
     * @param jsonInitData
     * @param renderStrategy    页面渲染策略
     * @param activityCallBack  自定义的Activity回调（Weex页面生命周期的监听）
     */
    public static void startWeexActivityByWebWeexUrl(
            @NonNull Context context
            , @NonNull String bundleUrl
            , @NonNull String pageName
            , @Nullable Map<String, Object> options
            , @Nullable final String jsonInitData
            , @NonNull WXRenderStrategy renderStrategy
            , @Nullable IWXRenderListener activityCallBack){
        getInstance();
        //使用内部引用去传递，并将自身对象传递到intent中，保证不被销毁。
        if (activityCallBack == null){
            activityCallBack = mInstance;
        }
        mInstance.setLoadByWebUrl(0);
        mInstance.setActivityCallBakc(activityCallBack);
        mInstance.setOptions(options);
        mInstance.setJsonInitData(jsonInitData);
        mInstance.setRenderStrategy(renderStrategy);
        Intent intent = new Intent();
        intent.putExtra("pageName", pageName);

        /**
         * 模板地址 http://dotwe.org/vue/38e202c16bdfefbdb88a8754f975454c
         */
        intent.putExtra("bundleUrl", bundleUrl);
        intent.putExtra("manager", mInstance);
        intent.setClass(context, __WeexMainActivity.class);
        try {
            context.startActivity(intent);
        }catch (ActivityNotFoundException e){
            Log.w(LOG_TAG, "该Activity已被用户禁用，或者，类已丢失。");
        }
    }

    /**
     * 详见重载方法
     * {@link WeexActivityManager#startWeexActivityByWebWeexUrl(android.content.Context, java.lang.String, java.lang.String, java.util.Map, java.lang.String, com.taobao.weex.common.WXRenderStrategy, com.taobao.weex.IWXRenderListener)}
     */
    public static void startWeexActivityByWebWeexUrl(
            Context context
            , String bundleUrl
            , String pageName
            , Map<String, Object> options
            , final String jsonInitData){
        startWeexActivityByWebWeexUrl(context, bundleUrl, pageName, options, jsonInitData, WXRenderStrategy.APPEND_ASYNC, null);
    }


    /**
     *  打开指定的服务器端存储的Weex页面
     *  样例代码：
             WeexActivityManager
                 .startWeexActivityByAssertFilePath(
                         mContext
                         , "examples.weex.js"
                         , "test"
                         , null
                         , null);
     *
     *
     * @param context   用于startActivity的Activity上下文，例如：MainActivity.this
     * @param localjsPATH 本地的位于assert目录下的js bundle文件路径，例：位于assert目录下有一文件：example.js。则此处传入"example.js"
     * @param pageName  当前页面的名称，用于调试信息的定位
     * @param options
     * @param jsonInitData
     * @param renderStrategy    页面渲染策略
     * @param activityCallBack  自定义的Activity回调（Weex页面生命周期的监听）
     */
    public static void startWeexActivityByAssertFilePath(
            @NonNull Context context
            , @NonNull String localjsPATH
            , @NonNull String pageName
            , @Nullable Map<String, Object> options
            , @Nullable final String jsonInitData
            , @NonNull WXRenderStrategy renderStrategy
            , @Nullable IWXRenderListener activityCallBack){
        getInstance();
        //使用内部引用去传递，并将自身对象传递到intent中，保证不被销毁。
        if (activityCallBack == null){
            activityCallBack = mInstance;
        }
        mInstance.setLoadByWebUrl(1);
        mInstance.setActivityCallBakc(activityCallBack);
        mInstance.setOptions(options);
        mInstance.setJsonInitData(jsonInitData);
        mInstance.setRenderStrategy(renderStrategy);
        Intent intent = new Intent();
        intent.putExtra("pageName", pageName);

        intent.putExtra("localjsPATH", localjsPATH);
        intent.putExtra("manager", mInstance);
        intent.setClass(context, __WeexMainActivity.class);
        try {
            context.startActivity(intent);
        }catch (ActivityNotFoundException e){
            Log.w(LOG_TAG, "该Activity已被用户禁用，或者，类已丢失。");
        }
    }

    /**
     * 详见重载方法
     * {@link WeexActivityManager#startWeexActivityByAssertFilePath(android.content.Context, java.lang.String, java.lang.String, java.util.Map, java.lang.String, com.taobao.weex.common.WXRenderStrategy, com.taobao.weex.IWXRenderListener)}
     */
    public static void startWeexActivityByAssertFilePath(
            Context context
            , String localjsPATH
            , String pageName
            , Map<String, Object> options
            , final String jsonInitData){
        startWeexActivityByAssertFilePath(context, localjsPATH, pageName, options, jsonInitData, WXRenderStrategy.APPEND_ASYNC, null);
    }





    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        Log.d(LOG_TAG, "Weex页面创建成功.");
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        Log.d(LOG_TAG, "Weex页面绘制成功."
                +"\n\t当前页面宽度："+width
                +"\n\t当前页面高度："+height);
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        Log.d(LOG_TAG, "Weex页面刷新成功."
                +"\n\t当前页面宽度："+width
                +"\n\t当前页面高度："+height);
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        Log.e(LOG_TAG, "Weex页面异常，"
                +"\n\t异常代码："+errCode
                +"\n\t异常信息："+msg);

    }

    private static WeexActivityManager getInstance() {
        if (mInstance == null){
            mInstance = new WeexActivityManager();
        }
        return mInstance;
    }

    public int getLoadByWebUrl() {
        return loadByWebUrl;
    }

    private void setLoadByWebUrl(int loadByWebUrl) {
        this.loadByWebUrl = loadByWebUrl;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    private void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public IWXRenderListener getActivityCallBakc() {
        return activityCallBakc;
    }

    private void setActivityCallBakc(IWXRenderListener activityCallBakc) {
        this.activityCallBakc = activityCallBakc;
    }

    public String getJsonInitData() {
        return jsonInitData;
    }

    private void setJsonInitData(String jsonInitData) {
        this.jsonInitData = jsonInitData;
    }

    public WXRenderStrategy getRenderStrategy() {
        return renderStrategy;
    }

    private void setRenderStrategy(WXRenderStrategy renderStrategy) {
        this.renderStrategy = renderStrategy;
    }
}
