package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class setChest extends AppCompatActivity {

    Button addExercise, delExercise, saveExercise;
    EditText getExercise, delExerciseInput, repsInput, weightInput;
    Spinner exSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_chest);

        //Initialize the DB, and an ArrayList with all the data from the DB
        TinyDB tinydb = new TinyDB(getApplicationContext());
        ArrayList<String> exercises = new ArrayList<>(tinydb.getListString("chestExercises"));
        //Initialize references for all the views
        getExercise = findViewById(R.id.exerciseInput);
        addExercise = findViewById(R.id.addEx);
        repsInput = findViewById(R.id.repsInput);
        weightInput = findViewById(R.id.weightInput);
        delExercise = findViewById(R.id.removeEx);
        delExerciseInput = findViewById(R.id.deleteExInput);
        saveExercise = findViewById(R.id.saveExercise);
        exSpinner = findViewById(R.id.firstExSpinner);
        //A check so we will always have the "Choose .." option
        if(!exercises.contains("Choose .."))
            exercises.add(0, "Choose ..");

        //Initialize Adapter for the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, exercises);

        //Sets the spinner adapter
        exSpinner.setAdapter(adapter);

        //Adds an exercise
        addExercise.setOnClickListener(v -> {
            //Gets the data from the input
            String newExercise = getExercise.getText().toString();
            //if the list doesn't have that exercise already
            if(!exercises.contains(newExercise)){
                //Add the exercise name to the list
                exercises.add(newExercise);
                //Add the exercise name to the db
                tinydb.putListString("chestExercises", exercises);
                //Pops a toast the we added the exercise successfully
                Toast.makeText(getBaseContext(), "Exercise Added", Toast.LENGTH_SHORT).show();
            }
        });
        //Deletes an exercise
        delExercise.setOnClickListener(v ->{
            //If we delete an item that is being selected at the moment
            try{
                if(exSpinner.getSelectedItem().toString()
                        .equals(delExerciseInput.getText().toString())){
                    exSpinner.setSelection(0);
                }
            }
            //It's prone to be dangerous, so we catch the exception
            catch(Exception e){
                Log.d("TAG", "onCreate: "+e);
            }
            //If there's no such exercise, alert
            if(!exercises.contains(delExerciseInput.getText().toString())
                    || delExerciseInput.getText().toString().equals("Choose ..")){
                Toast.makeText(getBaseContext(), "No Such Exercise", Toast.LENGTH_SHORT).show();
            }
            //Else, delete
            else{
                exercises.remove(delExerciseInput.getText().toString());
                tinydb.putListString("chestExercises", exercises);
                Toast.makeText(getBaseContext(), "Exercise Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        //Saves a new exercise, and goes back to where we came from
        saveExercise.setOnClickListener(view ->{
            //In case no exercise was chosen
            if(exSpinner.getSelectedItem().toString().equals("Choose ..")){
                Toast.makeText(getBaseContext(), "Please Choose an Exercise !",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                //Creates a new object of type Exercise with data from the input
                Exercise e1 = new Exercise(exSpinner.getSelectedItem().toString(),
                        Integer.parseInt(repsInput.getText().toString()),
                        Integer.parseInt(weightInput.getText().toString()));
                //Makes a new intent to send the data back
                Intent sendExercise = new Intent(this, backActivity.class);
                //Puts the data on the intent
                sendExercise.putExtra("exercise", e1);
                //Pops a toast to the screen
                Toast.makeText(getBaseContext(), "Saved!", Toast.LENGTH_SHORT).show();
                //Sends the intent back to where we called it from
                setResult(RESULT_OK, sendExercise);
                //Kills intent instance
                finish();
            }
        });
    }
}