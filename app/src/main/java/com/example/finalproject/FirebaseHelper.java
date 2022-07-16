package com.example.finalproject;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirebaseHelper {
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    public static List<Map<String,String>> fromDBList;
    public static List<Map<String,Long>> fromDBNums;


    public FirebaseHelper(final String path)
    {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(path);
        Log.d("FB", "FirebaseHelper: Connected");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                fromDBList = new ArrayList<>();
                fromDBNums = new ArrayList<>();

                Log.d("FB", "onDataChange: STARTED DOWNLOADING..");



                for (DataSnapshot data : dataSnapshot.getChildren()) { //בפועל יורד הדאטהבייס
                    fromDBList.add((Map) data.getValue());
                    fromDBNums.add((Map) data.getValue());

                }
                Log.d("FB", "onDataChange: DOWNLOADED ALL LISTS AND ARE READY");



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("FB", "Failed to read value.", error.toException());
            }
        });
    }

    public static List<User> convertToUserList() {
        List<User> userList = new ArrayList<>();
        for(int i=0;i<fromDBList.size();i++)//ריצה על רשימת המפות - צריך לוודא שהיא מוכנה
        {

            User temp = new User(fromDBList.get(i).get("username"),fromDBList.get(i).get("password"),fromDBNums.get(i).get("age"),fromDBList.get(i).get("mail"), Math.toIntExact(fromDBNums.get(i).get("accessLevel")));
            userList.add(temp);
        }
        return userList;
    }

    public static boolean login(String username,String password) {
        if(myRef != null && LoggedInUser.dbUserList != null)
        {
            for (User u: LoggedInUser.dbUserList) {
                if(u.getUsername().equals(username) && u.getPassword().equals(password))
                {
                    Log.d("FB", "login: Login successful");
                    LoggedInUser.loggedUser=u;
                    return true;
                }
            }
            Log.d("FB", "login: Login failed, User or pass incorrect");
            return false;
        }
        Log.e("FB", "login: Login error! FB or UserList aren't ready");
        return false;

    }
    public static String isExists(String name,double age,String mail) {
        for (int i = 0; i < fromDBList.size(); i++) {
            if(Objects.equals(fromDBList.get(i).get("username"), name) &&
                    Objects.equals(fromDBList.get(i).get("mail"), mail) &&
                    fromDBNums.get(i).get("age") == age)
                return fromDBList.get(i).get("password");
        }

        return null;
    }
    public static boolean isExists(String name) {
        for (Map<String,String> map: fromDBList) {
            if(Objects.equals(map.get("username"), name)) {
                Log.d("FB", "isExists: Username already exists");
                return true;

            }
        }
        return false;
    }

    public static boolean register(User user) {
        if(myRef != null){
            try{
                myRef.push().setValue(user);
                Log.d("FB", "register success! for"+user);
                return true;
            }
            catch (Exception e)
            {
                Log.e("FB", "register failed");
                return false;
            }
        }
        return false;

    }


}
