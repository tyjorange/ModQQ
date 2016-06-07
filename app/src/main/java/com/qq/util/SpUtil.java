package com.qq.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 除了SQLite数据库外，SharedPreferences也是一种轻型的数据存储方式，
 * 它的本质是基于XML文件存储key-value键值对数据，通常用来存储一些简单的配置信息。
 * 其存储位置在/data/data/<包名>/shared_prefs目录下。
 * SharedPreferences对象本身只能获取数据而不支持存储和修改，存储修改是通过Editor对象实现。
 * 　    一、根据Context获取SharedPreferences对象
 * 　　二、利用edit()方法获取Editor对象。
 * 　　三、通过Editor对象存储key-value键值对数据。
 * 　　四、通过commit()方法提交数据。
 */
public class SpUtil {
    private static final String NAME = "QQ";
    private static SpUtil instance;

    static {
        instance = new SpUtil();
    }

    public static SpUtil getInstance() {
        if (instance == null) {
            instance = new SpUtil();
        }
        return instance;
    }

    public static SharedPreferences getSharePreference(Context context) {
        /**
         * NAME 为创建的XML文件的名称
         * Context.MODE_PRIVATE 为默认操作模式,代表该文件是私有数据,只能被应用本身访问,在该模式下,写入的内容会覆盖原文件的内容
         * Context.MODE_APPEND 模式会检查文件是否存在,存在就往文件追加内容,否则就创建新文件.
         * Context.MODE_WORLD_READABLE 表示当前文件可以被其他应用读取.
         * Context.MODE_WORLD_WRITEABLE 表示当前文件可以被其他应用写入.
         */
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static boolean isFirst(SharedPreferences sharedPreferences) {
        //如果isFirst不存在，则返回值为false
        return sharedPreferences.getBoolean("isFirst", false);
    }

    public static void setStringSharedSharedPreference(SharedPreferences sharedPreferences, String key, String value) {
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setIntSharedSharedPreference(SharedPreferences sharedPreferences, String key, int value) {
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setBooleanSharedPreference(SharedPreferences sharedPreferences, String key, boolean value) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
