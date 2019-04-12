package com.example.param.recruit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EmployeeRegister extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);
    }

    public void registerClicked(View v){
        Intent myIntent = new Intent(this, EmployeeRegister.class);
        startActivity(myIntent);
    }
}
