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

public class fbExerciseHelper {
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    public static List<String> dbExerciseList;

    public fbExerciseHelper(final String path)
    {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(path);
        Log.d("FBE", "FirebaseHelper: Connected to Database");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                dbExerciseList = new ArrayList<String>();

                Log.d("FBE", "onDataChange: Downloading Exercises..");

                for (DataSnapshot data : dataSnapshot.getChildren()) { //בפועל יורד הדאטהבייס
                    Log.d("FBE", "onDataChange: "+data.getValue());
                    dbExerciseList.add(data.getValue().toString());
                }
                Log.d("FBE", "onDataChange: Finished Downloading Exercises List !");

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("FBE", "Failed to read values.", error.toException());
            }
        });
    }
//
//    public static List<User> convertToUserList() {
//        List<User> userList = new ArrayList<>();
//        for(int i=0;i<fromDBList.size();i++)//ריצה על רשימת המפות - צריך לוודא שהיא מוכנה
//        {
//            User temp = new User(fromDBList.get(i).get("userName"),fromDBList.get(i).get("password"),fromDBNums.get(i).get("age"),fromDBList.get(i).get("mail"), Math.toIntExact(fromDBNums.get(i).get("accessLevel")));
//            userList.add(temp);
//        }
//        Log.d("FB", "convertToUserList: "+userList);
//        return userList;
//    }
//
//    public static boolean login(String userName,String password) {
//        if(myRef != null && LoggedInUser.dbUserList != null)
//        {
//            for (User u: LoggedInUser.dbUserList) {
//                if(u.getUserName().equals(userName) && u.getPassword().equals(password))
//                {
//                    Log.d("FB", "login: Login successful");
//                    LoggedInUser.loggedUser=u;
//                    return true;
//                }
//            }
//            Log.d("FB", "login: Login failed, User or pass incorrect");
//            return false;
//        }
//        Log.e("FB", "login: Login error! FB or UserList aren't ready");
//        return false;
//
//    }
//    public static String isExists(String name,double age,String mail) {
//        for (int i = 0; i < fromDBList.size(); i++) {
//            if(Objects.equals(fromDBList.get(i).get("userName"), name) &&
//                    Objects.equals(fromDBList.get(i).get("mail"), mail) &&
//                    fromDBNums.get(i).get("age") == age)
//                return fromDBList.get(i).get("password");
//        }
//
//        return null;
//    }
    public static boolean isExists(String name) {
        for (int i = 0; i < dbExerciseList.size(); i++) {
            if(dbExerciseList.get(i).equals(name)) {
                Log.d("FBE", "isExists: userName already exists");
                return true;
            }
        }
        return false;
    }
//
//    public static boolean register(User user) {
//        if(myRef != null){
//            try{
//                myRef.push().setValue(user);
//                Log.d("FB", "register success! for"+user);
//                return true;
//            }
//            catch (Exception e)
//            {
//                Log.e("FB", "register failed");
//                return false;
//            }
//        }
//        return false;
//    }

    public static boolean addExerciseObject(Exercise ex){
        if(myRef != null){
            try{
                myRef.push().setValue(ex);
                Log.d("FBE", "Exercise Object added successfully");
                return true;
            }
            catch (Exception e){
                Log.e("FBE", "Failed to add exercise object, reason : "+e);
                return false;
            }
        }
        return false;
    }

    public static boolean addExerciseName(String exerciseName){
        if(myRef != null){
            try {
                myRef.push().setValue(exerciseName);
                Log.d("FBE", "Exercise name added successfully");
                return true;
            }
            catch (Exception e){
                Log.e("FBE", "Failed to add exercise name, reason : "+e);
                return false;
            }
        }
        return false;
    }

    public static ArrayList<String> getExercisesNames(){
        ArrayList<String> exNameList = new ArrayList<>();
        for(int i = 0; i < dbExerciseList.size(); i++)
        {
            String temp = dbExerciseList.get(i);
            exNameList.add(temp);
        }
        return exNameList;
    }

    public static void deleteExerciseName(String name){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate over exercise names in the Database
                for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()){
                    //If the current value of the line in the database matches the
                    //name we want to delete..
                    if(Objects.equals(exerciseSnapshot.getValue(), name)){
                        exerciseSnapshot.getRef().removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FBE", "Didn't Delete" );
            };

        });
    }

}
