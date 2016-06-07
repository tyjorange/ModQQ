package com.qq;

import com.qq.activity.LoginActivity;
import com.qq.fragment.ContactFatherFragment;
import com.qq.fragment.DynamicFragment;
import com.qq.fragment.NewsFatherFragment;
import com.qq.fragment.SettingFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class MainActivity extends FragmentActivity {
    protected static final String TAG = "MainActivity";
    private Context mContext;
    private ImageButton mNews, mContact, mDynamic, mSetting;
    private View mPopView;
    private View currentButtonView;

    private LinearLayout bottomBarGroup;
    private TextView app_cancel;
    private TextView app_change;
    private TextView app_exit;

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        findView();
        init();
    }

    private void findView() {
        // 消息。联系人。动态。我
        bottomBarGroup = (LinearLayout) findViewById(R.id.bottom_bar_group);
        mNews = (ImageButton) findViewById(R.id.button_news);
        mContact = (ImageButton) findViewById(R.id.button_contact);
        mDynamic = (ImageButton) findViewById(R.id.button_dynamic);
        mSetting = (ImageButton) findViewById(R.id.button_setting);
        // 底部弹出(退出 注销)菜单
        mPopView = LayoutInflater.from(mContext).inflate(R.layout.app_exit_pop_view, null);
        app_cancel = (TextView) mPopView.findViewById(R.id.app_cancel);
        app_change = (TextView) mPopView.findViewById(R.id.app_change_user);
        app_exit = (TextView) mPopView.findViewById(R.id.app_exit);
    }

    private void init() {
        mNews.setOnClickListener(newsOnClickListener);
        mContact.setOnClickListener(contactOnClickListener);
        mDynamic.setOnClickListener(dynamicOnClickListener);
        mSetting.setOnClickListener(settingOnClickListener);

        mNews.performClick();

        // PopupWindow这个类用来实现一个弹出框，可以使用任意布局的View作为其内容，这个弹出框是悬浮在当前activity之上的。
        mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);

        app_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //disposes PopupWindow
                mPopupWindow.dismiss();
            }
        });

        app_change.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                //设置activity进入和退出时的动画
                //它必需紧挨着startActivity()或者finish()函数之后调用
                ((Activity) mContext).overridePendingTransition(R.anim.activity_up, R.anim.activity_down);
                finish();
            }
        });

        app_exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private OnClickListener newsOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            NewsFatherFragment nff = new NewsFatherFragment();
            // replace()添加一个fragment到对应的container中去，
            // 只不过比add多了一步对相同containerViewId中已有的fragment检索，进行removeFragment操作，再去添加这个新来的fragment
            ft.replace(R.id.fl_content, nff, MainActivity.TAG);
            ft.commit();
            setCurrentButton(v);
        }
    };

    private OnClickListener contactOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ContactFatherFragment cff = new ContactFatherFragment();
            ft.replace(R.id.fl_content, cff, MainActivity.TAG);
            ft.commit();
            setCurrentButton(v);

        }
    };

    private OnClickListener dynamicOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            DynamicFragment df = new DynamicFragment();
            ft.replace(R.id.fl_content, df, MainActivity.TAG);
            ft.commit();
            setCurrentButton(v);
        }
    };

    private OnClickListener settingOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            SettingFragment sf = new SettingFragment();
            ft.replace(R.id.fl_content, sf, MainActivity.TAG);
            ft.commit();
            setCurrentButton(v);

        }
    };

    private void setCurrentButton(View v) {
        if (currentButtonView != null && currentButtonView.getId() != v.getId()) {
            currentButtonView.setEnabled(true);
        }
        //防止重复点击当前按钮
        v.setEnabled(false);
        currentButtonView = v;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果点击的是菜单键
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // 这个函数可是吊了，这个函数不只能设置背景……，因为你加上它之后，setOutsideTouchable（）才会生效;
            // 而且，只有加上它之后，PopupWindow才会对手机的返回按钮有响应：即，点击手机返回按钮，可以关闭PopupWindow；
            // 如果不加setBackgroundDrawable（）将关闭的PopupWindow所在的Activity.
            // 这个函数要怎么用，这里应该就不用讲了吧，可以填充进去各种Drawable,比如new BitmapDrawable()，new ColorDrawable(),等；
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b0000000")));
            //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
            mPopupWindow.showAtLocation(bottomBarGroup, Gravity.BOTTOM, 0, 0);
            //设置动画效果
            mPopupWindow.setAnimationStyle(R.style.app_pop);
            //这个函数的意义，就是指，PopupWindow以外的区域是否可点击，即如果点击PopupWindow以外的区域，PopupWindow是否会消失。
            mPopupWindow.setOutsideTouchable(true);
            //该函数的意义表示，PopupWindow是否具有获取焦点的能力，默认为False。
            mPopupWindow.setFocusable(true);
            mPopupWindow.update();
        }
        return super.onKeyDown(keyCode, event);
    }

}
