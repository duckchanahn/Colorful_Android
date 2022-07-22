package com.example.colorful_android.model;

public class User {

    private int userId;
    private String userName;
    private String personalColor;
    private String psychologiclaColor;

    public User() {
        this.userId = 0;
        this.userName = "";
        this.personalColor = "";
        this.psychologiclaColor = "";
    }

    public User(int userId, String userName, String personalColor, String psychologiclaColor) {
        this.userId = userId;
        this.userName = userName;
        this.personalColor = personalColor;
        this.psychologiclaColor = psychologiclaColor;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonalColor() {
        return personalColor;
    }

    public void setPersonalColor(String personalColor) {
        this.personalColor = personalColor;
    }

    public String getPsychologiclaColor() {
        return psychologiclaColor;
    }

    public void setPsychologiclaColor(String psychologiclaColor) {
        this.psychologiclaColor = psychologiclaColor;
    }
}
