package com.sinitek.mix.innerAC;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.sinitek.mix.R;

/* * *
 * created by:   cf.yao
 * on:           2019.三月.28   16:45
 * Des:          用于处理weex跳转到native的页面请求。（中转页！！！无实际页面）
 *
 * * */

public class __WeexToNativeForwardProxyActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weex_to_native_forward);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Intent intent = getIntent();
        if (intent == null){
            return;
        }
        //解析intent信息并做相应的跳转：

    }

}
