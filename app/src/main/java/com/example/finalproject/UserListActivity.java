package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;


public class UserListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        listView=findViewById(R.id.listView);
        UserAdapter adapter =new UserAdapter(this,R.layout.list_item,LoggedInUser.dbUserList);

        listView.setAdapter(adapter);

    }
}