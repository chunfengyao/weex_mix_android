package com.sinitek.mix.innerAC;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.sinitek.mix.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* * *
 * created by:   cf.yao
 * on:           2019.三月.28   16:45
 * Des:          用于处理weex跳转到native的页面请求。（中转页！！！无实际页面）
 *
 * * */

public class __WeexToNativeForwardProxyActivity extends FragmentActivity {
    private static final String SCHEME_HTTP = "http";
    private static final String SCHEME_HTTPS = "https";
    private static final String SCHEME_FILE = "file";
    private static final String SCHEME_LOCAL = "local";
    private static final String SCHEME_DATA = "data";
    private static final String SCHEME_WEEX = "weex";

    private static final String LOG_TAG = "Weex跳转中转页（DeepLink）";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent == null || intent.getData() == null){
            //如果是异常到了该页面，直接退出。
            finish();
            return;
        }
        setContentView(R.layout.activity_weex_to_native_forward);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //解析intent信息并做相应的跳转：
        Uri result = getIntent().getData();
        Map<String, Object> paramMap = new HashMap<>();
        if (result != null){
            Set<String> paramList = result.getQueryParameterNames();
            Log.d(LOG_TAG, "\n\tURL:"+getIntent().getDataString());
            Log.d(LOG_TAG, "\n\t当前协议为:"+getIntent().getData().getScheme());
            //来自推送通知的触发。
            if (paramList != null && paramList.size() > 0
                    && getIntent().getDataString() != null){
                Log.i(LOG_TAG, "收到的参数个数：" + paramList.size()+"");
                int index = 0;
                for (String param: paramList){
                    index++;
                    paramMap.put(param, result.getQueryParameter(param));
                    Log.d(LOG_TAG, "Query-" + index + "：" + param + " : " + result.getQueryParameter(param));
                }
                proxyToWeexAC(result, paramMap);
            }
        }
    }

    private void proxyToWeexAC(Uri data, Map<String, Object> paramMap){
        if (data.getScheme() == null || "".equals(data.getScheme().trim())){
            return;
        }
        switch (data.getScheme()){
            case SCHEME_HTTP:
            case SCHEME_HTTPS:{
                WeexActivityManager
                        .startWeexActivityByWebWeexUrl(
                                this
                                , data.toString()
                                , "fromProxyPage"
                                , paramMap
                                , null);
                break;
            }
            case SCHEME_FILE:
            case SCHEME_DATA:
            case SCHEME_LOCAL:{
                WeexActivityManager
                        .startWeexActivityByAssertFilePath(
                                this
                                , data.getPath()
                                , "fromProxyPage"
                                , paramMap
                                , null);
                break;
            }
            case SCHEME_WEEX:{
                break;
            }
            default:{
                Log.w(LOG_TAG, "捕获到了未匹配的Deeplink协议，协议名："+data.getScheme());
                break;
            }
        }
        //清理当前页面。
        finish();
    }

}
