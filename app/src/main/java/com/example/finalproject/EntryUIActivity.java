package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EntryUIActivity extends AppCompatActivity {

    Intent moveToLogin, moveToRegister, moveToUserList;
    Button entryLoginButton, entryRegisterButton, userListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entryui);

        moveToLogin = new Intent(this, LoginActivity.class);
        moveToRegister = new Intent(this, RegisterActivity.class);
        moveToUserList = new Intent(this, UserListActivity.class);
        entryLoginButton = findViewById(R.id.entryLoginButton);
        entryRegisterButton = findViewById(R.id.entryRegisterButton);
        userListButton = findViewById(R.id.userListButton);

        if(LoggedInUser.loggedUser != null){
            entryLoginButton.setText("Logout");
            if(LoggedInUser.loggedUser.getAccessLevel() == 1)
                userListButton.setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(this, "Please Log In !", Toast.LENGTH_SHORT).show();
            entryLoginButton.setText("Login");
        }

        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        LoggedInUser.loggedUser = null; //Forcing a log out
                        entryLoginButton.setText("Logout");
                        startActivity(new Intent(getBaseContext(), EntryUIActivity.class));
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(EntryUIActivity.this, "Didn't Log Out (:", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Are you sure you want to logout ?")
                .setPositiveButton("Yes", dialogListener)
                .setNegativeButton("No", dialogListener);

        entryLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LoggedInUser.loggedUser == null){
                    startActivity(new Intent(getBaseContext(), EntryUIActivity.class));
                    finish(); //finish Activity.
                    startActivity(moveToLogin);
                }
                else{
                    //dialog show
                    dialogBuilder.show();
                }
            }
        });

        entryRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), EntryUIActivity.class));
                finish(); //finish Activity.
                startActivity(moveToRegister);
            }
        });

        userListButton.setOnClickListener(v -> startActivity(moveToUserList));
    }
}