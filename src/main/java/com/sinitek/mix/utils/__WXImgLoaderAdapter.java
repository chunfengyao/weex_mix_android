package com.sinitek.mix.utils;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.sinitek.mix.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

class __WXImgLoaderAdapter implements IWXImgLoaderAdapter {
    private static final String LOG_TAG = "Weex默认图片加载适配器";
    private static final Picasso.Listener tmpListener = new Picasso.Listener() {
        @Override
        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
            Log.e(LOG_TAG, "位于：" + uri.toString() + "\t处的图片加载失败！", exception);
        }
    };
    @Override
    public void setImage(final String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
        //判空
        if (url == null){
            return;
        }
        String tmpUrl = new String(url);
        if ("".equals(tmpUrl.trim())){
            return;
        }
        Picasso.with(view.getContext())
                .load(Uri.parse(url))

                .error(view.getContext().getResources().getDrawable(R.drawable.weex_error))
                .into(view);
//        new Picasso.Builder(view.getContext())
//                .listener(tmpListener)
//                .build()
//                .load(Uri.parse(url))
//                .error(view.getContext().getResources().getDrawable(R.drawable.weex_error))
////                .memoryPolicy(MemoryPolicy.NO_STORE)
//                //取消对磁盘缓存的屏蔽，减少服务器负担。
////                .networkPolicy(NetworkPolicy.NO_STORE)
//                .into(view);
    }
}













