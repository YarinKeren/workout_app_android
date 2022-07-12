package com.example.finalproject;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class LoggedInUser {
    public static User loggedUser;
    public static List<User> dbUserList; // for login
    public static List<String> dbUserSt; // for admin panel

    public static void prepare()
    {
        dbUserList = FirebaseHelper.convertToUserList();
        Log.d("FB", "prepare: USER LIST IS READY!");
    }
    public static void prepareSt()
    {
        dbUserSt = new ArrayList<>();
        if(dbUserList != null)
        {
            for (User u: dbUserList) {
                String temp = u.getUsername()+":"+u.getPassword();
                temp += u.getAccessLevel()==1 ? " (Admin)":"";
                dbUserSt.add(temp);
            }


        }
        Log.d("FB", "prepare: USER STRING LIST IS READY!");
    }
}
