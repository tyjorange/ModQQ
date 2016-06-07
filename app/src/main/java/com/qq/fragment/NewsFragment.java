package com.qq.fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.qq.AsyncTaskBase;
import com.qq.R;
import com.qq.adapter.NewsAdapter;
import com.qq.bean.RecentChat;
import com.qq.test.TestData;
import com.qq.view.CustomListView;
import com.qq.view.CustomListView.OnRefreshListener;
import com.qq.view.LoadingView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewsFragment extends Fragment {
    private static final String TAG = "NewsFragment";
    private Context mContext;
    private View mBaseView;
    private CustomListView mCustomListView;
    private LoadingView mLoadingView;
    private View mSearchView;
    private NewsAdapter adapter;
    private LinkedList<RecentChat> chats = new LinkedList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mSearchView = inflater.inflate(R.layout.common_search_l, null);
        mBaseView = inflater.inflate(R.layout.fragment_news, null);
        findView();
        init();
        return mBaseView;
    }

    private void findView() {
        mCustomListView = (CustomListView) mBaseView.findViewById(R.id.lv_news);
        mLoadingView = (LoadingView) mBaseView.findViewById(R.id.loading);
    }

    private void init() {
        adapter = new NewsAdapter(mContext, chats, mCustomListView);
        mCustomListView.setAdapter(adapter);

        mCustomListView.addHeaderView(mSearchView);
        mCustomListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncRefresh().execute(0, 100, 2);
            }
        });
        mCustomListView.setCanLoadMore(false);
        new NewsAsyncTask(mLoadingView).execute();
    }

    /**
     * 加载NewsFragment时刷新聊天记录Task
     * NewsAsyncTask
     */
    private class NewsAsyncTask extends AsyncTaskBase {
        List<RecentChat> recentChats = new ArrayList<>();

        /**
         * @param loadingView 下拉刷新头 view
         */
        public NewsAsyncTask(LoadingView loadingView) {
            super(loadingView);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            int result = -1;
            recentChats = TestData.getRecentChats();
            if (recentChats.size() > 0) {
                result = 1;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            chats.addAll(recentChats);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }

    /**
     * 下拉刷新聊天记录Task
     * AsyncRefreshTask
     */
    private class AsyncRefresh extends AsyncTask<Integer, String, List<RecentChat>> {

        @Override
        protected List<RecentChat> doInBackground(Integer... params) {
            // doInBackground()并不运行在UI线程当中，主要用于异步操作，
            // 所以在该方法中不能对UI线程进行设置和修改
            List<RecentChat> recentChats = TestData.getRecentChats();
            try {
                for (int i = params[0]; i <= params[1]; i += params[2]) {
                    publishProgress(i + "%");
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return recentChats;
        }

        @Override
        protected void onPostExecute(List<RecentChat> recentChats) {
            super.onPostExecute(recentChats);
            if (recentChats != null) {
                chats.clear();
                for (RecentChat rc : recentChats) {
                    chats.addFirst(rc);
                }
                adapter.notifyDataSetChanged();
                mCustomListView.onRefreshComplete();
            }
            Log.i(TAG, "异步操作执行结束");
        }

        @Override
        protected void onProgressUpdate(String... values) {
            // 在doInBackground方法当中，每次调用publishProgress()方法都会触发onProgressUpdate执行
            // onProgressUpdate是在UI线程中执行，所以可以对UI线程进行操作
            super.onProgressUpdate(values);
            Log.i(TAG, values[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "开始执行异步线程");
        }

    }

}
