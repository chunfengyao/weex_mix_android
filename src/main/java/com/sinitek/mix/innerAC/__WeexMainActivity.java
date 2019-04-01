package com.sinitek.mix.innerAC;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sinitek.mix.R;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import junit.framework.Assert;

import java.util.Map;

public class __WeexMainActivity extends Activity implements IWXRenderListener {
    private static final String LOG_TAG = "WeexMainActivity页面";
    private WXSDKInstance mWXSDKInstance;
    private IWXRenderListener activityCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //可以考虑替换为透明页面（要看具体是几级页面。）
        setContentView(R.layout.activity_weex_main);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        showWeexPage();
    }

    private void showWeexPage(){
        String pageName = getIntent().getStringExtra("pageName");
        String bundleUrl = getIntent().getStringExtra("bundleUrl");
        String localjsPATH = getIntent().getStringExtra("localjsPATH");
        Log.d(LOG_TAG, "pageName:"+pageName);
        Log.d(LOG_TAG, "bundleUrl:"+bundleUrl);
        Log.d(LOG_TAG, "localjsPATH:"+localjsPATH);
        WeexActivityManager manager = (WeexActivityManager)getIntent().getSerializableExtra("manager");
        if (manager == null || manager.getLoadByWebUrl() < 0){
            finish();
            return;
        }
        String jsonInitData = manager.getJsonInitData();
        Map<String, Object> options = manager.getOptions();
        activityCallBack = manager.getActivityCallBakc();
        WXRenderStrategy renderStrategy = manager.getRenderStrategy();

        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);
        switch (manager.getLoadByWebUrl()){
            case 0:{
                if (bundleUrl == null || "".equals(bundleUrl.trim())){
                    finish();
                    return;
                }
                mWXSDKInstance.renderByUrl(pageName, bundleUrl, options, jsonInitData, renderStrategy);
                break;
            }
            case 1:{
                if (localjsPATH == null || "".equals(localjsPATH.trim())){
                    finish();
                    return;
                }
                mWXSDKInstance.render(
                        pageName
                        , WXFileUtils.loadAsset(localjsPATH, this)
                        , options
                        , jsonInitData
                        , renderStrategy);
                break;
            }
            default:{
                Assert.assertEquals("未处理传入的LoadByWebUrl标志位！！！", manager.getLoadByWebUrl(), -1);
                Assert.fail("出现了未处理的值！！！");
                Log.w(LOG_TAG, "无法处理当前类型的页面请求！！！");
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        Log.d(LOG_TAG, "Weex页面创建成功.");
        setContentView(view);
        if (activityCallBack != null){
            activityCallBack.onViewCreated(instance, view);
        }
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        Log.d(LOG_TAG, "Weex页面绘制成功."
                +"\n\t当前页面宽度："+width
                +"\n\t当前页面高度："+height);
        if (activityCallBack != null){
            activityCallBack.onRenderSuccess(instance, width, height);
        }
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        Log.d(LOG_TAG, "Weex页面刷新成功."
                +"\n\t当前页面宽度："+width
                +"\n\t当前页面高度："+height);
        if (activityCallBack != null){
            activityCallBack.onRefreshSuccess(instance, width, height);
        }
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        Log.e(LOG_TAG, "Weex页面异常，"
                +"\n\t异常代码：" + errCode
                +"\n\t异常信息："+msg);
        if (activityCallBack != null){
            activityCallBack.onException(instance, errCode, msg);
        }
        instance.onActivityDestroy();
        whenWeexPageRenderFailed();

    }


    private void whenWeexPageRenderFailed(){
        Toast.makeText(
                getApplicationContext()
                , "抱歉，页面加载失败了，即将退出该页面。"
                , Toast.LENGTH_LONG)
                .show();
        new Thread(
                new Runnable(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }finally {
                            __WeexMainActivity.this.finish();
                        }
                    }
                }).start();
    }

}