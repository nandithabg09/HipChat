package com.android.hipchat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TitleExtractor {


    private final Handler mCmdHandler;
    private Activity mContext = null;
    private  JSONObject sLinksObject = null;


    private  LinearLayout webViewLayout = null;
    private ArrayList<String> urlList;
    private static ArrayList<JSONObject> webObjects = new ArrayList<>();


    public TitleExtractor(Activity context, Handler handler) {
        mContext = context;
        mCmdHandler = handler;
    }
    public void extractTitles(String chatMsg) throws JSONException {
        System.out.println(" extractTitles " + chatMsg);
        sLinksObject = null;
        urlList = null;
        webObjects.clear();
        sLinksObject = null;
        webViewLayout = (LinearLayout) mContext.findViewById(R.id.webViewLayout);
        System.out.println(" extractTitles " + chatMsg);
        if (webViewLayout.getChildCount() > 0)
        webViewLayout.removeAllViews();
       // webView = (WebView) mContext.findViewById(R.id.webView);
        urlList =  getURL(chatMsg);
        if (urlList != null) {
            for (int index = 0; index < urlList.size(); index++) {

                WebView webView = new WebView(mContext);
                webView.setId(index);
                webViewLayout.addView(webView);
                if (webView != null) {
                    webView.setWebViewClient(new CustomBrowser(urlList.size()));
                    webView.getSettings().setLoadsImagesAutomatically(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    webView.loadUrl(urlList.get(index));
                }
                System.out.println(" mentions3 " );
            }
        } else sendResult();
    }

    public JSONObject getLinksObject() {
        return sLinksObject;
    }

private void sendResult() {
    Message mMessage = new Message();
    mMessage.what = Constants.SET_RESULT;
    mCmdHandler.sendMessage(mMessage);
}
    private ArrayList<String> getURL(String chatMsg) {
        ArrayList<String> urlList = new ArrayList<>();
        String[] splitString = chatMsg.split("\\s");
        for (String url : splitString) {
            System.out.println(" url " + url);
            if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
                urlList.add(url);
            }
        }//end of for loop

        return ((urlList.size() > 0)? urlList : null);
    }

    private  class CustomBrowser extends WebViewClient {
        private int urlListSize ;
        public CustomBrowser(int numberOfURLs) {
            super();
            urlListSize = numberOfURLs;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title = view.getTitle();
            System.out.println(urlListSize+"urlListSize onPageFinished title " + title+" view.getId()"+view.getId());

                try {
                    if (title != null && title.length()>0) {
                        JSONObject webJSON = new JSONObject();
                        webJSON.put(Constants.sJsonURL, url);
                        webJSON.put(Constants.sJsonTitle, title);
                        webObjects.add(webJSON);
                    }
                    if (view.getId() == (urlListSize - 1)) {
                        if (webObjects.size() == 0) {
                            sendResult();
                            return;
                        }
                        System.out.println(" en do loading  " + title);
                        sLinksObject = new JSONObject();
                        sLinksObject.put(Constants.sJsonLinks, new JSONArray(webObjects));
                        sendResult();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    sendResult();
                }

        }
    }



}