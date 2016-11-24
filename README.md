# WebView
WebView、WebViewClient and WebChormeClient 常用知识点
## 概述
WebView是Android平台上一个特殊的View,可以用来显示网页
## WebView、WebViewClient、WebChormeClient
WebViewClient和WebChormeClient用来辅助WebView
* WebView职责:解析、渲染网页
* WebViewClient:辅助WebView处理各种事件、通知
* WebChromeClient:辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等

### WebView常用方法
设置编码
<pre><code>mWebView.getSettings().setDefaultTextEncodingName("utf-8");</code></pre>
支持js
<pre><code>mWebView.getSettings().setJavaScriptEnabled(true);</code></pre>
设置本地调用对象及其接口(重要知识点跟JS交互就靠他)
<pre><code>mWebView.addJavascriptInterface(new Object(), "myObj");</code></pre>
加载本地网页
<pre><code>mWebView.loadUrl("file:///android_asset/wb.html");</code></pre>
加载网络地址
<pre><code>mWebView.loadUrl("http://www.baidu.com");</code></pre>
加载网页格式的文本
<pre><code>String summary = "<html><body>You scored <b>192</b> points.</body></html>";
                   mWebView.loadData(summary, "text/html", null);</code></pre>
设置WebViewClient
<pre><code>mWebView.setWebViewClient(mWvClient);</code></pre>
设置WebChromeClient
<pre><code>mWebView.setWebChromeClient(mChrome);</code></pre>
### WebViewClient常见方法
加载资源事件 url:链接
<pre><code>public void onLoadResource(WebView view, String url)</code></pre>
请求url开始
<pre><code>public void onPageStarted(WebView view, String url, Bitmap favicon)</code></pre>
请求url结束
<pre><code>public void onPageFinished(WebView view, String url)</code></pre>
请求错误信息
<pre><code>public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)  -23+
  public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse)  -23+
  public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)  -23-</code></pre>
打开方式（程序还是系统浏览器）
<pre><code>public boolean shouldOverrideUrlLoading</code></pre>
### WebChormeClient常见方法
获取网页头部icon
<pre><code>public void onReceivedIcon(WebView view, Bitmap icon)</code></pre>
获取头部标题
<pre><code>public void onReceivedTitle(WebView view, String title)</code></pre>
网页加载进度变化
<pre><code>public void onProgressChanged(WebView view, int newProgress)</code></pre>
