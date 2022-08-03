package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
@SuppressWarnings("InstantiationOfUtilityClass")
public class setChest extends AppCompatActivity {

    fbExerciseHelper fbh1;
    fbExerciseObjectHelper fbh2;
    Button addExercise, delExercise, saveExercise;
    EditText getExercise, delExerciseInput, repsInput, weightInput;
    Spinner exSpinner;
    ArrayList<String> exercisesNames;
    ArrayList<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_chest);

        //Gets a reference to the firebase database
        fbh1 = new fbExerciseHelper("ChestExercisesNames");
        fbh2 = new fbExerciseObjectHelper("ChestExerciseObject");

        //Initialize references for all the views
        getExercise = findViewById(R.id.exerciseInput);
        addExercise = findViewById(R.id.addEx);
        repsInput = findViewById(R.id.repsInput);
        weightInput = findViewById(R.id.weightInput);
        delExercise = findViewById(R.id.removeEx);
        delExerciseInput = findViewById(R.id.deleteExInput);
        saveExercise = findViewById(R.id.saveExercise);
        exSpinner = findViewById(R.id.firstExSpinner);

        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                //Adds exercise name to the exercise names list
                exercisesNames = new ArrayList<>(fbExerciseHelper.getExercisesNames());
                exercises = new ArrayList<>(fbExerciseObjectHelper.getExerciseList());
                updateList();
                //Adds an exercise
                addExercise.setOnClickListener(v -> {
                    //Gets the data from the input
                    String newExercise = getExercise.getText().toString();

                    //Adds the exercise name to the Database
                    if(fbExerciseHelper.doesntExist(newExercise)){
                        //Adds the exercise to the list
                        fbExerciseHelper.addExerciseName(newExercise);
                        //Pops a message that we added the exercise
                        Toast.makeText(getBaseContext(), "Exercise Added", Toast.LENGTH_SHORT).show();
                        getExercise.setText("");// Cleans the EditText
                        updateList();
                    }
                    else{
                        Toast.makeText(getBaseContext(), "Exercise already exists !", Toast.LENGTH_SHORT).show();
                    }
                });

                //Deletes an exercise
                delExercise.setOnClickListener(v ->{
                    String exToDelete = delExerciseInput.getText().toString();
                    //If we delete an item that is being selected at the moment
                    try{
                        if(exSpinner.getSelectedItem().toString()
                                .equals(exToDelete)){
                            exSpinner.setSelection(0);
                        }
                    }
                    //It's prone to be dangerous, so we catch the exception
                    catch(Exception e){
                        Log.d("TAG", "onCreate: "+e);
                    }
                    //If there's no such exercise, alert
                    if(fbExerciseHelper.doesntExist(exToDelete) || exToDelete.equals("Choose ..")){
                        //Clears the EditText
                        delExerciseInput.setText("");
                        //Pops a message
                        Toast.makeText(getBaseContext(), "No Such Exercise", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //Removes the exercise from the list
                        fbExerciseHelper.deleteExerciseName(exToDelete);
                        //Updates the list
                        updateList();
                        //Clears the EditText
                        delExerciseInput.setText("");
                        //Pops a message
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
                                Double.parseDouble(repsInput.getText().toString()),
                                Double.parseDouble(weightInput.getText().toString()));

                        //********TRIAL********

                        //Adds the exercise object
                        if (exercises.size() < 4)
                            fbExerciseObjectHelper.addExerciseObject(e1);
                        else{
                            //Gets data from intent
                            Bundle extras = getIntent().getExtras();
                            //If the data is not null
                            if (extras != null) {
                                int num = extras.getInt("num");
                                //Replaces object in the Database
                                fbExerciseObjectHelper.replaceExerciseObject(num, e1);
                            }
                        }

                        //********TRIAL********

                        //Makes a new intent to send the data back
                        Intent sendExercise = new Intent(getBaseContext(), chestActivity.class);
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
        }; //end of thread
        //Handling the thread (Makes delay)
        new Handler().postDelayed(t, 500);

    }
    public void updateList(){
        //Opening a thread since we need the list of exercisesNames
        //to be Re-Downloaded..
        Thread t1 = new Thread(){
            @Override
            public void run() {
                super.run();
                //Updates exercisesNames list
                exercisesNames = new ArrayList<>(fbExerciseHelper.getExercisesNames());
                //A check so we will always have the "Choose .." option
                if(!exercisesNames.contains("Choose .."))
                    exercisesNames.add(0, "Choose ..");
                //Initialize Adapter for the SpinnerView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                        R.layout.support_simple_spinner_dropdown_item, exercisesNames);

                //Sets the SpinnerView adapter
                exSpinner.setAdapter(adapter);
            }
        };
        //Puts a small delay for the thread
        new Handler().postDelayed(t1, 500);
    }
}