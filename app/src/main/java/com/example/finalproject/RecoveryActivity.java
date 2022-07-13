package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class RecoveryActivity extends AppCompatActivity {
    EditText userInput,ageInput,mailInput,okPass;
    Button recoverButton;
    TextView usernameText, mailText, ageText, passRecovered;
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

        recoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameEdit = userInput.getText().toString();
                String mailEdit = mailInput.getText().toString();
                double ageEdit = Double.valueOf(ageInput.getText().toString());

                //Checks if all data is correct, if so - returns the password, else returns null
                String status = FirebaseHelper.isExists(usernameEdit, ageEdit, mailEdit);
                if (status != null)
                {
                    okPass.setText(status);
                    okPass.setVisibility(View.VISIBLE);
                    usernameText.setVisibility(View.INVISIBLE);
                    mailText.setVisibility(View.INVISIBLE);
                    ageText.setVisibility(View.INVISIBLE);
                    userInput.setVisibility(View.INVISIBLE);
                    ageInput.setVisibility(View.INVISIBLE);
                    mailInput.setVisibility(View.INVISIBLE);
                    recoverButton.setVisibility(View.INVISIBLE);
                    passRecovered.setVisibility(View.VISIBLE);
//                    Snackbar.make(getCurrentFocus(),"Password Recovered (:", BaseTransientBottomBar.LENGTH_LONG).show();
                }
                else
                    Snackbar.make(getCurrentFocus(),"Unable to recover password", BaseTransientBottomBar.LENGTH_LONG).show();


            }
        });
    }
}