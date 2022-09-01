package com.example.colorful_android.DTO;

public class Customer {

    private static final Customer customer = new Customer();

    private int customerId;
    private String token;
    private String customerName;
    private String userId;
    private String personalColor;
    private String psycologicalColor;
    private String loginType;

    public Customer() {
        this.customerId = 0;
        this.token = "";
        this.customerName = "";
        this.userId =  "";
        this.personalColor = "";
        this.psycologicalColor = "";
        this.loginType = "";
    }

    public void setInstance(int customerId, String token, String customerName, String userId, String personalColor, String psycologicalColor, String loginType) {
        this.customerId = customerId;
        this.token = token;
        this.customerName = customerName;
        this.userId = userId;
        this.personalColor = personalColor;
        this.psycologicalColor = psycologicalColor;
        this.loginType = loginType;
    }

    public void setInstance(Customer customer) {
        this.customer.setCustomerId(customer.getCustomerId());
        this.customer.setToken(customer.getToken());
        this.customer.setCustomerName(customer.getCustomerName());
        this.customer.setUserId(customer.getUserId());
        this.customer.setPersonalColor(customer.getPersonalColor());
        this.customer.setPsycologicalColor(customer.getPsycologicalColor()); ;
        this.customer.setLoginType(customer.getLoginType());
    }

    public static Customer getInstance() {return customer;}


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPersonalColor() {
        return personalColor;
    }

    public void setPersonalColor(String personalColor) {
        this.personalColor = personalColor;
    }

    public String getPsycologicalColor() {
        return psycologicalColor;
    }

    public void setPsycologicalColor(String psycologicalColor) {
        this.psycologicalColor = psycologicalColor;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
