package com.example.colorful_android.Model;

public class User {

    private static final User user = new User();

    private int customerId;
    private int userId; // 사용자 고유 ID를 넣는데 사실 customerId에 넣어도 돼서
    private String userName; // 이름
    private String personalColor;
    private String psychologiclaColor;

    public static User getInstance() { // 테스트
        return user;
    }


    public static User getInstance(int customerId, String userName, String personalColor, String psychologiclaColor) {
        user.setCustomerId(customerId);
        user.setUserName(userName);
        user.setPersonalColor(personalColor);
        user.setPsychologiclaColor(psychologiclaColor);

        return user;
    }


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

    public int getCustomerId() {return customerId;}

    public void setCustomerId(int customerId) {this.customerId = customerId;}

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
