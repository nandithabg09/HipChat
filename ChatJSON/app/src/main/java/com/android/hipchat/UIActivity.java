package com.android.hipchat;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UIActivity extends AppCompatActivity {

    private JSONObject mentions = null;
    private JSONObject emotIcons = null;
    private JSONObject links = null;

    private Spinner mChatMessage = null;
    private TextView mResult = null;
    private static ProgressDialog mProgressDialog;
private TitleExtractor extractor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        addItemsOnSpinner();
        mResult = (TextView)findViewById(R.id.result);
         extractor = new TitleExtractor(UIActivity.this, mCmdHandler);
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        mChatMessage = (Spinner) findViewById(R.id.inputText);
        List<String> list = new ArrayList<String>();
        list.add("@chris you around?");
        list.add("Good morning! (megusta) (coffee)");
        list.add("Good morning! (megusta) (coffee) https://www.wikipedia.org/");
        list.add("Olympics are starting soon; http://www.nbcolympics.com");
        list.add("@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mChatMessage.setAdapter(dataAdapter);
    }


    public void onClick(View view) {
        popProgressDlg();
        System.out.println("called*****************************************************************");
        //String input = "@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016";
        String chatMsg = (String) mChatMessage.getSelectedItem();
        try {
            mentions = MentionsExtractor.extractMentions(chatMsg);
            emotIcons = EmotIconsExtractor.extractEmotIcons(chatMsg);
            extractor.extractTitles(chatMsg);

        } catch (Exception e) {
            e.printStackTrace();
            links = null;
        }
    }

    private Handler mCmdHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           //"-- handlMessage() START --"
            if (msg.what == Constants.SET_RESULT) {
                try {
                    mProgressDialog.dismiss();
                    setResult();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private void setResult() throws JSONException {
        StringBuilder result = new StringBuilder();
        if (mentions != null) {
            result.append(mentions.toString(1));
        }
        if (emotIcons != null) {
            result.append(emotIcons.toString(1));
        }
        links = extractor.getLinksObject();
        if (links != null) {
            result.append(links.toString(1));
        }
        mResult.setText(result);

    }

    public void popProgressDlg() {
        //VRLog.w(TAG, "-- popProgressDlg() START --");

            mProgressDialog = new ProgressDialog(this);
            if (mProgressDialog != null) {
                mProgressDialog.setMessage(getString(R.string.please_wait));
                mProgressDialog.setCancelable(false);
            }

            if (mProgressDialog != null) {
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.show();
            }

    }

}
