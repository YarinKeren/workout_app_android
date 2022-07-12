package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    EditText username,password;
    FirebaseHelper fbh;
    Thread thread;
    Handler handler;
    TextView recoverPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fbh = new FirebaseHelper("Users"); // takes time...
        username = findViewById(R.id.userNameInput);
        password = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        recoverPassword = findViewById(R.id.forgotPassword);

//        recoverPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getBaseContext(),RecoveryActivity.class));
//            }
//        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(user.length() < 3) {
                    Toast.makeText(LoginActivity.this, "Username is too short", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.length()<1){
                    Toast.makeText(LoginActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        LoggedInUser.prepare(); //right before login attempt
                        LoggedInUser.prepareSt();
                        boolean status = FirebaseHelper.login(user,pass);

                        if(status)
                        {
                            Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "Hello "+LoggedInUser.loggedUser.getUsername(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getBaseContext(),MainActivity.class));
                        }
                        else Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                    }
                }; //end of thread

                handler = new Handler();
                handler.postDelayed(thread,1000);
            };

        });




    }
}