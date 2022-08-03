package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecoveryActivity extends AppCompatActivity {
    EditText userInput,ageInput,mailInput,okPass;
    Button recoverButton, tryAgainButton;
    TextView usernameText, mailText, ageText, passRecovered, cantRecover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        userInput = findViewById(R.id.recoveryUsernameInput);
        ageInput = findViewById(R.id.recoveryAgeInput);
        mailInput = findViewById(R.id.recoveryMailInput);
        recoverButton = findViewById(R.id.recoverButton);
        okPass = findViewById(R.id.okPass);
        usernameText = findViewById(R.id.usernameText);
        mailText = findViewById(R.id.mailText);
        ageText = findViewById(R.id.ageText);
        passRecovered = findViewById(R.id.passwordRecoveredText);
        cantRecover = findViewById(R.id.cantRecoverText);
        tryAgainButton = findViewById(R.id.tryAgainButton);

        recoverButton.setOnClickListener(v -> {
            String usernameEdit = userInput.getText().toString();
            String mailEdit = mailInput.getText().toString();
            double ageEdit = Double.parseDouble(ageInput.getText().toString());

            //Checks if all data is correct, if so - returns the password, else returns null
            String status = FirebaseHelper.isExists(usernameEdit, ageEdit, mailEdit);
            if (status != null)
            {
                okPass.setText(status);
                okPass.setVisibility(View.VISIBLE);
                toggleVisibility();
                passRecovered.setVisibility(View.VISIBLE);
            }
            else{
                Toast.makeText(RecoveryActivity.this, "Unable to recover password", Toast.LENGTH_SHORT).show();
                toggleVisibility();
                cantRecover.setVisibility(View.VISIBLE);
                tryAgainButton.setVisibility(View.VISIBLE);
                tryAgainButton.setOnClickListener(v1 -> {
                    //Refresh the activity
                    finish();
                    startActivity(getIntent());
                });
            }


        });

    }
    public void toggleVisibility(){
        usernameText.setVisibility(View.INVISIBLE);
        mailText.setVisibility(View.INVISIBLE);
        ageText.setVisibility(View.INVISIBLE);
        userInput.setVisibility(View.INVISIBLE);
        ageInput.setVisibility(View.INVISIBLE);
        mailInput.setVisibility(View.INVISIBLE);
        recoverButton.setVisibility(View.INVISIBLE);
    }

}