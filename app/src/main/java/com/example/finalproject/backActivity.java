package com.example.finalproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class backActivity extends AppCompatActivity {

    //    EditText exerciseTV;
    TextView repsInput, weightInput, exerciseTV, repsInput2,
            weightInput2, exerciseTV2, repsInput3, weightInput3,
            exerciseTV3, repsInput4, weightInput4, exerciseTV4;
    fbExerciseObjectHelper fbh2;
    ArrayList<Exercise> exList;
    ArrayList<Object>[] allExercises;

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);

        //Makes an instance of the tinyDB to store objects (Acts like SharedPreferences)
        TinyDB tinydb = new TinyDB(getApplicationContext());

        fbh2 = new fbExerciseObjectHelper("ExerciseObject");


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

        //*****TRIAL*****
        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                exList = fbExerciseObjectHelper.getExerciseList();

                //A list to store all exercise objects, getting them from the tinydb
                allExercises =
                        new ArrayList[]{tinydb.getListObject("allBackExercises", Exercise.class)};

                //Showing the exercises that are saved in the tinyDB
                if (!exList.isEmpty()) {
                    repsInput.setVisibility(View.VISIBLE);
                    weightInput.setVisibility(View.VISIBLE);
                    exerciseTV.setText(((Exercise) exList.get(0)).getExerciseName());
                    repsInput.setText(String.valueOf(Integer.valueOf((int) ((Exercise) exList.get(0)).getReps()).intValue()));
                    weightInput.setText(((Exercise) exList.get(0)).getWeight() +"KG");
                    if(exList.size() > 1){
                        repsInput2.setVisibility(View.VISIBLE);
                        weightInput2.setVisibility(View.VISIBLE);
                        exerciseTV2.setText(((Exercise) exList.get(1)).getExerciseName());
                        repsInput2.setText(String.valueOf(Integer.valueOf((int) ((Exercise) exList.get(1)).getReps()).intValue()));
                        weightInput2.setText(((Exercise) exList.get(1)).getWeight() +"KG");
                    }
                    if(exList.size() > 2){
                        repsInput3.setVisibility(View.VISIBLE);
                        weightInput3.setVisibility(View.VISIBLE);
                        exerciseTV3.setText(((Exercise) exList.get(2)).getExerciseName());
                        repsInput3.setText(String.valueOf(Integer.valueOf((int) ((Exercise) exList.get(2)).getReps()).intValue()));
                        weightInput3.setText(((Exercise) exList.get(2)).getWeight() +"KG");
                    }
                    if(exList.size() > 3){
                        repsInput4.setVisibility(View.VISIBLE);
                        weightInput4.setVisibility(View.VISIBLE);
                        exerciseTV4.setText(((Exercise) exList.get(3)).getExerciseName());
                        repsInput4.setText(String.valueOf(Integer.valueOf((int) ((Exercise) exList.get(3)).getReps()).intValue()));
                        weightInput4.setText(((Exercise) exList.get(3)).getWeight() +"KG");
                    }
                }
            }
        };
        new Handler().postDelayed(t, 500);
        //*****TRIAL*****

        // Create launcher variable , gets back from intent to the current spot/call
        ActivityResultLauncher<Intent> getFirstExercise = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //Gets the intent data into "data" variable
                        Intent data = result.getData();
                        //Calls a function to handle the data
                        handleIntentData(0, data);
                    }
                });

        //Moving to the set page, going back to where the launcher points at
        exerciseTV.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), setBack.class);
            intent.putExtra("num", 0);
            getFirstExercise.launch(intent);
        });

        // Create launcher variable , gets back from intent to the current spot/call
        ActivityResultLauncher<Intent> getSecondExercise = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //Calls a function to handle the data
                        Intent data = result.getData();
                        handleIntentData(1, data);
                    }
                });

        exerciseTV2.setOnClickListener(view -> {
            if(!exerciseTV.getText().toString().equals("New Exercise")){
                Intent intent = new Intent(getBaseContext(), setBack.class);
                intent.putExtra("num", 1);
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
                        handleIntentData(2, data);
                    }
                });

        exerciseTV3.setOnClickListener(view -> {
            if(!exerciseTV2.getText().toString().equals("New Exercise")){
                Intent intent = new Intent(getBaseContext(), setBack.class);
                intent.putExtra("num", 2);
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
                        handleIntentData(3, data);
                    }
                });

        exerciseTV4.setOnClickListener(view -> {
            if(!exerciseTV3.getText().toString().equals("New Exercise")){
                Intent intent = new Intent(getBaseContext(), setBack.class);
                intent.putExtra("num", 3);
                getFourthExercise.launch(intent);
            }
            else{
                Toast.makeText(getBaseContext(), "Input 3rd Exercise before", Toast.LENGTH_SHORT).show();
            }
        });

    }//Closes onCreate
    //---GLOBAL SCOPE---
    @SuppressLint("SetTextI18n")
    public void handleIntentData(int num, Intent data){

        final Exercise[] ex = new Exercise[1];

        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                //Gets the data from the intent
                ex[0] = data.getParcelableExtra("exercise");
                //Gets the list from the Database
                exList = fbExerciseObjectHelper.getExerciseList();

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
                //Updates the TextView
                if (ex[0] != null) {
                    repsInput.setVisibility(View.VISIBLE);
                    weightInput.setVisibility(View.VISIBLE);
                    exerciseTV.setText(ex[0].getExerciseName());
                    repsInput.setText(String.valueOf(Integer.valueOf((int) ((Exercise) exList.get(num)).getReps()).intValue()));
                    weightInput.setText(((Exercise) exList.get(num)).getWeight() +"KG");
                }
            }
        };
        //Delays...
        new Handler().postDelayed(t, 400);
    }
}