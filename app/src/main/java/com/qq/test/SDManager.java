package com.qq.test;

import android.content.Context;

import com.qq.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SDManager {
    private Context mContext;
    private String[] names = {"songhuiqiao.jpg", "zhangzetian.jpg", "songqian.jpg", "hangxiaozhu.jpg", "jingtian.jpg"
            , "liuyifei.jpg", "kangyikun.jpg", "dengziqi.jpg"};

    public SDManager(Context context) {
        this.mContext = context;
    }

    /**
     * 从Assets目录复制头像文件到指定目录
     */
    public void moveUserIcon() {
        String path = FileUtil.getRecentChatPath();
        InputStream is = null;
        FileOutputStream out = null;
        for (int i = 0; i < 8; i++) {
            try {
                //open(String fileName) 使用 ACCESS_STREAMING模式打开assets下的指定文件。.
                is = mContext.getResources().getAssets().open(names[i]);
                out = new FileOutputStream(new File(path + names[i]));
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                    out.flush();
                }
                is.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
