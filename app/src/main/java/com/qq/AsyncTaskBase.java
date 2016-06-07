package com.qq;

import com.qq.view.LoadingView;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

/**
 * 控制刷新头的隐藏/显示
 */
public class AsyncTaskBase extends AsyncTask<Integer, Integer, Integer> {
    private LoadingView mLoadingView;

    public AsyncTaskBase(LoadingView loadingView) {
        this.mLoadingView = loadingView;
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result == 1) {
            mLoadingView.setVisibility(View.GONE);
        } else {
            mLoadingView.setText(R.string.no_data);
        }
    }

    @Override
    protected void onPreExecute() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

}
