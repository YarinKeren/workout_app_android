package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EntryUIActivity extends AppCompatActivity {

    Intent moveToLogin, moveToRegister;
    Button entryLoginButton, entryRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entryui);

        moveToLogin = new Intent(this, LoginActivity.class);
        moveToRegister = new Intent(this, RegisterActivity.class);
        entryLoginButton = findViewById(R.id.entryLoginButton);
        entryRegisterButton = findViewById(R.id.entryRegisterButton);

        entryLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(moveToLogin);
            }
        });

        entryRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(moveToRegister);
            }
        });
    }
}