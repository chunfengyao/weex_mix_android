package com.sinitek.mix.modules;

import android.content.Intent;
import android.view.Menu;

import com.taobao.weex.common.WXModule;

import java.util.List;
import java.util.Map;

/* * *
 * created by:   cf.yao
 * on:           2019.三月.25   16:48
 * Des:          weex扩展（基类）
 *
 * * */

public abstract class BaseWeexModule extends WXModule {

    public BaseWeexModule() {
        super();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityCreate() {
        super.onActivityCreate();
    }

    @Override
    public void onActivityStart() {
        super.onActivityStart();
    }

    @Override
    public void onActivityPause() {
        super.onActivityPause();
    }

    @Override
    public void onActivityResume() {
        super.onActivityResume();
    }

    @Override
    public void onActivityStop() {
        super.onActivityStop();
    }

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
    }

    @Override
    public boolean onActivityBack() {
        return super.onActivityBack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void addEventListener(String eventName, String callback, Map<String, Object> options) {
        super.addEventListener(eventName, callback, options);
    }

    @Override
    public void removeAllEventListeners(String eventName) {
        super.removeAllEventListeners(eventName);
    }

    @Override
    public List<String> getEventCallbacks(String eventName) {
        return super.getEventCallbacks(eventName);
    }

    @Override
    public boolean isOnce(String callback) {
        return super.isOnce(callback);
    }

    @Override
    public String getModuleName() {
        return super.getModuleName();
    }

    @Override
    public void setModuleName(String moduleName) {
        super.setModuleName(moduleName);
    }
}
