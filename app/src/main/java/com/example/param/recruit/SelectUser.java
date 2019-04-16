package com.example.param.recruit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
    }


    public void needJobClicked(View V){
        Intent myIntent = new Intent(this, EmployeeLogin.class);
        startActivity(myIntent);
    }

    public void needEmployeeClicked(View V){
        Intent myIntent = new Intent(this, EmployerLogin.class);
        startActivity(myIntent);
    }


}
