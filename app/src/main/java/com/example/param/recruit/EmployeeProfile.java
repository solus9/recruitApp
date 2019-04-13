package com.example.param.recruit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeProfile extends AppCompatActivity {
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    EditText nameEditText, usernameEditText, passwordEditText, emailEditText, contactEditText, dobEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        nameEditText = findViewById(R.id.nameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        contactEditText = findViewById(R.id.contactEditText);
        dobEditText = findViewById(R.id.dobEditText);

        int employeeId = 1;
        toolbar = findViewById(R.id.toolBarEmployee);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerEmployeeLayout);
        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_employee_profile:
                        item.setChecked(true);
                        Intent myIntent = new Intent(EmployeeProfile.this, EmployeeProfile.class);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_employee_search:
                        item.setChecked(true);
                        myIntent = new Intent(EmployeeProfile.this, EmployeeSearch.class);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

        Call<JsonObject> call = RetrofitClient
                .getInstance()
                .getApi()
                .employeeDetails(employeeId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response != null) {
                    System.out.println(response.code());
                    String res = response.body().toString();
                    Log.d("OnResponse", res);
                    System.out.println(res);
                    JsonElement jelement = new JsonParser().parse(res);
                    JsonObject  jobject = jelement.getAsJsonObject();
                    if(jobject.get("code").getAsInt() == 1){
                        nameEditText.setText(jobject.get("name").getAsString());
                        usernameEditText.setText(jobject.get("username").getAsString());
                        passwordEditText.setText(jobject.get("password").getAsString());
                        emailEditText.setText(jobject.get("email").getAsString());
                        contactEditText.setText(jobject.get("contact").getAsString());
                        dobEditText.setText(jobject.get("dob").getAsString());
                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployeeProfile.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployeeProfile.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployeeProfile.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployeeProfile.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });
    }

    public void updateClicked(View v){

    }
}
