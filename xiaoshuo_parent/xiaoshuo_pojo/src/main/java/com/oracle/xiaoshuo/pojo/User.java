package com.oracle.xiaoshuo.pojo;

import java.io.Serializable;

public class User implements Serializable {
    private Integer UserId;
    private  String username;
    private  String password;
    private  Integer balance;
    private  Integer isWriter;
    private  String email;
    private Double myEarnings;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getIsWriter() {
        return isWriter;
    }

    public void setIsWriter(Integer isWriter) {
        this.isWriter = isWriter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getMyEarnings() {
        return myEarnings;
    }

    public void setMyEarnings(Double myEarnings) {
        this.myEarnings = myEarnings;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserId=" + UserId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", isWriter=" + isWriter +
                ", email='" + email + '\'' +
                ", myEarnings=" + myEarnings +
                '}';
    }
}
