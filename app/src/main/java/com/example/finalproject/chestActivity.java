package com.example.finalproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class chestActivity extends AppCompatActivity {

//    EditText exerciseTV;
    TextView repsInput, weightInput, exerciseTV, repsInput2,
        weightInput2, exerciseTV2, repsInput3, weightInput3,
        exerciseTV3, repsInput4, weightInput4, exerciseTV4;

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chest);
        //Makes an instance of the tinyDB to store objects (Acts like SharedPreferences)
        TinyDB tinydb = new TinyDB(getApplicationContext());
        //Initialize vars for views
        exerciseTV = findViewById(R.id.exerciseTV);
        repsInput = findViewById(R.id.repsTV);
        weightInput = findViewById(R.id.weightTV);
        exerciseTV2 = findViewById(R.id.exerciseTV1);
        repsInput2 = findViewById(R.id.repsTV1);
        weightInput2 = findViewById(R.id.weightTV1);
        exerciseTV3 = findViewById(R.id.exerciseTV2);
        repsInput3 = findViewById(R.id.repsTV2);
        weightInput3 = findViewById(R.id.weightTV2);
        exerciseTV4 = findViewById(R.id.exerciseTV3);
        repsInput4 = findViewById(R.id.repsTV3);
        weightInput4 = findViewById(R.id.weightTV3);

        //A list to store all exercise objects, getting them from the tinydb
        final ArrayList<Object>[] allExercises =
                new ArrayList[]{tinydb.getListObject("allChestExercises", Exercise.class)};

        //Showing the exercises that are saved in the tinyDB
        if (!allExercises[0].isEmpty()) {
            repsInput.setVisibility(View.VISIBLE);
            weightInput.setVisibility(View.VISIBLE);
            exerciseTV.setText(((Exercise) allExercises[0].get(0)).getExerciseName());
            repsInput.setText(String.valueOf(((Exercise) allExercises[0].get(0)).getReps()));
            weightInput.setText(((Exercise) allExercises[0].get(0)).getWeight() +"KG");
            if(allExercises[0].size() > 1){
                repsInput2.setVisibility(View.VISIBLE);
                weightInput2.setVisibility(View.VISIBLE);
                exerciseTV2.setText(((Exercise) allExercises[0].get(1)).getExerciseName());
                repsInput2.setText(String.valueOf(((Exercise) allExercises[0].get(1)).getReps()));
                weightInput2.setText(((Exercise) allExercises[0].get(1)).getWeight() +"KG");
            }
            if(allExercises[0].size() > 2){
                repsInput3.setVisibility(View.VISIBLE);
                weightInput3.setVisibility(View.VISIBLE);
                exerciseTV3.setText(((Exercise) allExercises[0].get(2)).getExerciseName());
                repsInput3.setText(String.valueOf(((Exercise) allExercises[0].get(2)).getReps()));
                weightInput3.setText(((Exercise) allExercises[0].get(2)).getWeight() +"KG");
            }
            if(allExercises[0].size() > 3){
                repsInput4.setVisibility(View.VISIBLE);
                weightInput4.setVisibility(View.VISIBLE);
                exerciseTV4.setText(((Exercise) allExercises[0].get(3)).getExerciseName());
                repsInput4.setText(String.valueOf(((Exercise) allExercises[0].get(3)).getReps()));
                weightInput4.setText(((Exercise) allExercises[0].get(3)).getWeight() +"KG");
            }
        }

        // Create launcher variable , gets back from intent to the current spot/call
        ActivityResultLauncher<Intent> getFirstExercise = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //Gets the intent data into "data" variable
                        Intent data = result.getData();
                        //Calls a function to handle the data
                        handleIntentData(data, 0, tinydb, allExercises);
                    }
                });

        //Moving to the set page, going back to where the launcher points at
        exerciseTV.setOnClickListener(view -> {
            Intent intent = new Intent(this, setChest.class);
            getFirstExercise.launch(intent);
        });

        // Create launcher variable , gets back from intent to the current spot/call
        ActivityResultLauncher<Intent> getSecondExercise = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //Gets the intent data into "data" variable
                        Intent data = result.getData();
                        //Calls a function to handle the data
                        handleIntentData(data, 1, tinydb, allExercises);
                    }
                });

        exerciseTV2.setOnClickListener(view -> {
            if(!exerciseTV.getText().toString().equals("New Exercise")){
                Intent intent = new Intent(this, setChest.class);
                getSecondExercise.launch(intent);
            }
            else{
                Toast.makeText(getBaseContext(), "Start at the 1st Exercise", Toast.LENGTH_SHORT).show();
            }
        });
        // Create launcher variable , gets back from intent to the current spot/call
        ActivityResultLauncher<Intent> getThirdExercise = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //Gets the intent data into "data" variable
                        Intent data = result.getData();
                        //Calls a function to handle the data
                        handleIntentData(data, 2, tinydb, allExercises);
                    }
                });

        exerciseTV3.setOnClickListener(view -> {
            if(!exerciseTV2.getText().toString().equals("New Exercise")){
                Intent intent = new Intent(this, setChest.class);
                getThirdExercise.launch(intent);
            }
            else{
                Toast.makeText(getBaseContext(), "Input 2nd Exercise before", Toast.LENGTH_SHORT).show();
            }
        });
        // Create launcher variable , gets back from intent to the current spot/call
        ActivityResultLauncher<Intent> getFourthExercise = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //Gets the intent data into "data" variable
                        Intent data = result.getData();
                        //Calls a function to handle the data
                        handleIntentData(data, 3, tinydb, allExercises);
                    }
                });

        exerciseTV4.setOnClickListener(view -> {
            if(!exerciseTV3.getText().toString().equals("New Exercise")){
                Intent intent = new Intent(this, setChest.class);
                getFourthExercise.launch(intent);
            }
            else{
                Toast.makeText(getBaseContext(), "Input 3rd Exercise before", Toast.LENGTH_SHORT).show();
            }
        });

    }//Closes onCreate
    //---GLOBAL SCOPE---
    //Changes actionbar color
    @SuppressLint("SetTextI18n")
    public void handleIntentData(Intent data, int num, TinyDB db, ArrayList<Object>[] lst){
        Exercise ex = null;
        //If we get correct data, assign it to the object
        if (data != null) {
            ex = data.getParcelableExtra("exercise");
        }
        //Save exercise object to DB
//        db.putObject("exercise", ex);
        //Updates the list
        if(lst[0].size() <= num || lst[0].isEmpty()){
            lst[0].add(0, ex);
        }
        else{
            lst[0].set(num, ex);
        }
        //Save the exercise to the list in the DB
        db.putListObject("allChestExercises", lst[0]);
        //This part dynamically reaches the TV were currently addressing
        String currentTV = (num == 0) ? "repsTV" : "repsTV" + num;
        int id = getResources().getIdentifier(currentTV, "id", getPackageName());
        TextView repsInput =  findViewById(id);
        currentTV = (num == 0) ? "weightTV" : "weightTV" + num;
        id = getResources().getIdentifier(currentTV, "id", getPackageName());
        TextView weightInput =  findViewById(id);
        currentTV = (num == 0) ? "exerciseTV" : "exerciseTV" + num;
        id = getResources().getIdentifier(currentTV, "id", getPackageName());
        TextView exerciseTV = findViewById(id);
        //Update the view
        if (ex != null) {
            repsInput.setVisibility(View.VISIBLE);
            weightInput.setVisibility(View.VISIBLE);
            exerciseTV.setText(ex.getExerciseName());
            repsInput.setText(String.valueOf(ex.getReps()));
            weightInput.setText(ex.getWeight() +"KG");
        }
    }
}