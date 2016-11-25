package com.yqy.test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * WebView、WebViewClient、WebChormeClient
 * WebView职责:解析、渲染网页
 * WebViewClient和WebChormeClient用来辅助WebView
 * WebViewClient:辅助WebView处理各种事件、通知
 * WebChromeClient:辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
 */
public class MainActivity extends Activity {

    private WebView mWebView;
    private ImageView mImageView;
    private TextView mTextView;
    private ProgressBar mProgressBar;

    private String errorUrl = "file:///android_asset/error.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.mWebView);
        mImageView = (ImageView) findViewById(R.id.mImageView);
        mTextView = (TextView) findViewById(R.id.mTextView);
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);

        //设置编码
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置背景颜色 透明
        mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        //设置本地调用对象及其接口
        //mWebView.addJavascriptInterface(new JavaScriptObject(this), "myObj");

        //加载本地网页
//        mWebView.loadUrl("file:///android_asset/wb.html");
        //加载网络地址
        mWebView.loadUrl("http://www.baidu.com");
        //加载网页格式的文本
//        String summary = "<html><body>You scored <b>192</b> points.</body></html>";
//        mWebView.loadData(summary, "text/html", null);

        //设置WebViewClient
        mWebView.setWebViewClient(mWvClient);

        //设置WebChromeClient
        mWebView.setWebChromeClient(mChrome);
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清除缓存
        mWebView.clearCache(true);

        //清除访问历史记录
        mWebView.clearHistory();

        //释放WebView占用的资源
        mWebView.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){ //拦截返回键
            if(mWebView.canGoBack()){
                mWebView.goBack();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * WebViewClient：辅助WebView处理各种事件、通知
     */
    WebViewClient mWvClient = new WebViewClient(){

        /**
         * 加载资源事件
         * 拦截 拦截 拦截！！！
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            Log.e("onLoadResource",url);
            //做一些处理
            super.onLoadResource(view, url);
        }

        /**
         * 请求url结束
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            Log.e("onPageFinished",url);
            //做一些处理
            super.onPageFinished(view, url);
        }

        /**
         * 请求url开始
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e("onPageStarted",url);
            //做一些处理
            mProgressBar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        /**
         * 请求错误信息
         * 23 以上会调用
         */
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.e("onReceivedError",error.getErrorCode()+ "-" + error.getDescription());
            view.loadUrl(errorUrl);
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            Log.e("onReceivedHttpError",errorResponse.getStatusCode() + "-" + errorResponse.getData());
            view.loadUrl(errorUrl);
            super.onReceivedHttpError(view, request, errorResponse);
        }

        /**
         * 请求失败信息
         * 23 以下会调用
         * @param view
         * @param errorCode 错误码
         * @param description 错误描述
         * @param failingUrl 请求失败的链接地址
         */
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.e("onReceivedError",errorCode + "-" + description);
            Log.e("onReceivedError",failingUrl );
            //当请求失败时显示我们的错误页面 浏览器的失败页面太丑 对用户来说 是一个很糟糕的体验
            if(!failingUrl.equals(errorUrl)) view.loadUrl(errorUrl);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        /**
         * Q:点击网页中超链接会自动打开系统默认的浏览器
         * A:重写shouldOverrideUrlLoading
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

    };

    /**
     * WebChromeClient:辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
     */
    WebChromeClient mChrome = new WebChromeClient(){

        /**
         * 获取头部icon
         * @param view
         * @param icon
         */
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            Log.e("onReceivedIcon","-");
            mImageView.setImageBitmap(icon);
            super.onReceivedIcon(view, icon);
        }

        /**
         * 获取头部标题
         * @param view
         * @param title
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            Log.e("onReceivedTitle",title);
            mTextView.setText(title);
            super.onReceivedTitle(view, title);
        }

        /**
         * 进度改变
         * @param view
         * @param newProgress 当前页面载入进度 为代表 一个0到100之间的整数
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.e("onProgressChanged",newProgress + "");
            if(newProgress == 100)
                mProgressBar.setVisibility(View.INVISIBLE);
            else
                mProgressBar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    };
}
