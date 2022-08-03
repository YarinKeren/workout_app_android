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
                dbExerciseList = new ArrayList<>();

                Log.d("FBE", "onDataChange: Downloading Exercises..");

                for (DataSnapshot data : dataSnapshot.getChildren()) { //בפועל יורד הדאטהבייס
                    Log.d("FBE", "onDataChange: "+data.getValue());
                    dbExerciseList.add(Objects.requireNonNull(data.getValue()).toString());
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
    public static boolean doesntExist(String name) {
        for (int i = 0; i < dbExerciseList.size(); i++) {
            if(dbExerciseList.get(i).equals(name)) {
                Log.d("FBE", "isExists: userName already exists");
                return false;
            }
        }
        return true;
    }
    public static void addExerciseName(String exerciseName){
        if(myRef != null){
            try {
                myRef.push().setValue(exerciseName);
                Log.d("FBE", "Exercise name added successfully");
            }
            catch (Exception e){
                Log.e("FBE", "Failed to add exercise name, reason : "+e);
            }
        }
    }

    public static ArrayList<String> getExercisesNames(){
        return new ArrayList<>(dbExerciseList);
    }
//    noinspection public static ArrayList<String> getExercisesNames(){
//        ArrayList<String> exNameList = new ArrayList<>();
//        for(int i = 0; i < dbExerciseList.size(); i++)
//        {
//            String temp = dbExerciseList.get(i);
//            exNameList.add(temp);
//        }
//        return exNameList;
//    }

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
            }

        });
    }

}
