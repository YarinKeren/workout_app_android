package com.example.finalproject;

public class User {

    private String username;
    private String password;
    private double age;
    private String mail;
    private int accessLevel;

    public User(String username, String password, double age, String mail) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.mail = mail;
        this.accessLevel = 0;
    }
    public User(String username, String password, double age, String mail,int accessLevel) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.mail = mail;
        this.accessLevel = accessLevel;
    }
    public User(String username, String password, String mail) {
        this.username = username;
        this.password = password;
        this.age = 0;
        this.mail = mail;
        this.accessLevel = 0;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getAccessLevel() {return accessLevel;}

    public void setAccessLevel(int accessLevel) { this.accessLevel = accessLevel;}

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", mail='" + mail + '\'' +
                ", accessLevel=" + accessLevel +
                '}';
    }
}