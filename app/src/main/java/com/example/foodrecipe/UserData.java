package com.example.foodrecipe;

public class UserData {
    private String Fullname;
    private String Email;
    private String Password;
    private String PhoneNumber;

    public UserData() {
    }

    public UserData(String fullname, String email, String password, String phoneNumber) {
        Fullname = fullname;
        Email = email;
        Password = password;
        PhoneNumber = phoneNumber;
    }

    public String getFullname() {
        return Fullname;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }
}
