package com.shiyuan.model;

public class User {
    private Long userId;

    private String userName;

    private Long userAccount;

    private String userPassword;

    private String userIcon;

    private Long userPhoneKey;

    private String userQqKey;

    private String userWeixinKey;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(Long userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public Long getUserPhoneKey() {
        return userPhoneKey;
    }

    public void setUserPhoneKey(Long userPhoneKey) {
        this.userPhoneKey = userPhoneKey;
    }

    public String getUserQqKey() {
        return userQqKey;
    }

    public void setUserQqKey(String userQqKey) {
        this.userQqKey = userQqKey;
    }

    public String getUserWeixinKey() {
        return userWeixinKey;
    }

    public void setUserWeixinKey(String userWeixinKey) {
        this.userWeixinKey = userWeixinKey;
    }
}