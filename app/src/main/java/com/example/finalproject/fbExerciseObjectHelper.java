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

public class fbExerciseObjectHelper {
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;
    public static List<Map<String,String>> dbExerciseObjectStringList;
    public static List<Map<String,Long>> dbExerciseObjectNumsList;

    public fbExerciseObjectHelper(final String path)
    {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(path);
        Log.d("FBOE", "FirebaseHelper: Connected");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                dbExerciseObjectStringList = new ArrayList<>();
                dbExerciseObjectNumsList = new ArrayList<>();

                Log.d("FBOE", "onDataChange: Started Downloading..");

                for (DataSnapshot data : dataSnapshot.getChildren()) { //בפועל יורד הדאטהבייס
                    dbExerciseObjectStringList.add((Map) data.getValue());
                    dbExerciseObjectNumsList.add((Map) data.getValue());
                }
                Log.d("FBOE", "onDataChange: Downloaded all Exercises..");

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("FBOE", "Failed to read value.", error.toException());
            }
        });
    }

    public static void addExerciseObject(Exercise ex){
        if(myRef != null){
            try{
                myRef.push().setValue(ex);
                Log.d("FBOE", "Exercise Object added successfully");
            }
            catch (Exception e){
                Log.e("FBOE", "Failed to add exercise object, reason : "+e);
            }
        }
    }

    public static ArrayList<Exercise> getExerciseList(){
        ArrayList<Exercise> exList = new ArrayList<>();
        for(int i = 0; i < dbExerciseObjectStringList.size(); i++)
        {
            Exercise temp = new Exercise(dbExerciseObjectStringList.get(i).get("exerciseName"), dbExerciseObjectNumsList.get(i).get("reps"), dbExerciseObjectNumsList.get(i).get("weight"));
            exList.add(temp);
        }
        Log.d("FBOE", "convertToUserList: "+exList);
        return exList;
    }

    public static void replaceExerciseObject(int num, Exercise ex){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Iterate over exercise names in the Database
                int i = 0;
                for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()){
                    //If the current value of the line in the database matches the
                    //name we want to delete..
                    if(i == num){
                        exerciseSnapshot.getRef().setValue(ex);
                    }
                    i++;

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FBE", "Didn't Delete" );
            }

        });
    }
}
