package com.qq.activity;


import com.qq.R;
import com.qq.test.SDManager;
import com.qq.util.SpUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

public class WelcomeActivity extends Activity {
    protected static final String TAG = "WelcomeActivity";
    private Context mContext;
    private ImageView mImageView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext = WelcomeActivity.this;
        findView();
        init();
    }

    private void findView() {
        mImageView = (ImageView) findViewById(R.id.iv_welcome);
    }

    @SuppressWarnings("static-access")
    private void init() {
        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = SpUtil.getInstance().getSharePreference(mContext);
                boolean isFirst = SpUtil.getInstance().isFirst(sharedPreferences);
                if (!isFirst) {
                    new SDManager(mContext).moveUserIcon();
                    SpUtil.getInstance().setBooleanSharedPreference(sharedPreferences, "isFirst", true);
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}
