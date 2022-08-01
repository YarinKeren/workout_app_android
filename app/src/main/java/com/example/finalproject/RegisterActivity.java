package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    FirebaseHelper fbh;
    Button registerButton;
    EditText username,
            password, repeatPassword,
            email, age;
    Intent moveToEntryUI;
    ImageView qm1, qm2, qm3, qm4, qm5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fbh = new FirebaseHelper("Users");

        moveToEntryUI = new Intent(this, EntryUIActivity.class);

        registerButton = findViewById(R.id.registerButton);
        username = findViewById(R.id.userNameInputRegister);
        email = findViewById(R.id.emailInputRegister);
        password = findViewById(R.id.passwordInputRegister);
        repeatPassword = findViewById(R.id.passwordInputRegister2);
        age = findViewById(R.id.ageInputRegister);
        handleQuestionMarks();

        registerButton.setOnClickListener(v -> {
            if(!password.getText().toString().equals(repeatPassword.getText().toString()))
                Toast.makeText(getBaseContext(),"Passwords don't match!",Toast.LENGTH_LONG).show();
            else
            {
                if(username.getText().length() < 3)
                    Toast.makeText(getBaseContext(),"Username is too short (3+ chars)",Toast.LENGTH_LONG).show();
                else if(!(email.getText().toString().contains("@") && email.getText().toString().contains(".")))
                    Toast.makeText(getBaseContext(),"Mail isn't in the correct format",Toast.LENGTH_LONG).show();
                else{
                    if(FirebaseHelper.isExists(username.getText().toString()))
                    {
                        Toast.makeText(RegisterActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //should be okay now - Create user
                    User tempUser = new User(username.getText().toString(),password.getText().toString(),Double.valueOf(age.getText().toString()),email.getText().toString());
                    boolean status = FirebaseHelper.register(tempUser);
                    if(status){
                        Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                        Handler handler = new Handler();
                        handler.postDelayed(() -> startActivity(moveToEntryUI), 2000);
                    }
                    else
                        Toast.makeText(RegisterActivity.this, "Registration Failed!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void handleQuestionMarks(){
        qm1 = findViewById(R.id.qm1);
        qm2 = findViewById(R.id.qm2);
        qm3 = findViewById(R.id.qm3);
        qm4 = findViewById(R.id.qm4);
        qm5 = findViewById(R.id.qm5);
        qm1.setTooltipText("Username must be at least 3 letters");
        qm2.setTooltipText("Password must be at least 3 letters");
        qm3.setTooltipText("Repeat password");
        qm4.setTooltipText("Enter your email address");
        qm5.setTooltipText("Enter your age");
    }
}