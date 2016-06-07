package com.qq.bean;

/**
 * 聊天消息实体
 */
public class RecentChat {
    private String userName;
    private String userFeel;
    private String userTime;
    private String imgPath;

    public RecentChat(String userName, String userFeel, String userTime, String img) {
        super();
        this.userName = userName;
        this.userFeel = userFeel;
        this.userTime = userTime;
        this.imgPath = img;
    }

    public String getUserName() {
        return userName;
    }


    public String getUserFeel() {
        return userFeel;
    }


    public String getUserTime() {
        return userTime;
    }


    public String getImgPath() {
        return imgPath;
    }

}
