package com.sinitek.mix.utils;

import android.util.Log;

import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.common.WXRequest;
import com.taobao.weex.common.WXResponse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

class __WXHttpAdapter implements IWXHttpAdapter {
    private static final String LOG_TAG = "Weex默认网络请求适配器";
    private static final OkHttpClient tmpHttpClient
            = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .build();
    @Override
    public void sendRequest(WXRequest request, final OnHttpListener listener) {
        FormBody.Builder tmpBuilder = new FormBody.Builder();
        //此处用途未知暂时使用默认适配器的100
        listener.onHttpUploadProgress(100);

        for (Map.Entry<String, String> entry : request.paramMap.entrySet()){
            tmpBuilder.add(entry.getKey(), entry.getValue());
        }
        listener.onHttpStart();
        tmpHttpClient.newCall(
                new Request
                        .Builder()
                        .url(request.url)
                        .post(tmpBuilder.build())
                        .build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(LOG_TAG, "位于：" + call.request().url().url().toString() + "\t处的请求失败！", e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        listener.onHttpResponseProgress(response.body().bytes().length);
                        listener.onHeadersReceived(response.code(), response.headers().toMultimap());
                        WXResponse weexResponse = new WXResponse();
                        weexResponse.data = response.body().string();
                        weexResponse.originalData = response.body().bytes();
                        listener.onHttpFinish(weexResponse);
                    }
                });

    }
}