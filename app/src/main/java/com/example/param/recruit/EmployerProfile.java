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

public class EmployerProfile extends AppCompatActivity {
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    EditText nameEditText, usernameEditText, passwordEditText, emailEditText, contactEditText, dobEditText, orgEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile);

        nameEditText = findViewById(R.id.orgnameEditText);
        usernameEditText = findViewById(R.id.jobtypeEditText);
        passwordEditText = findViewById(R.id.descriptionEditText);
        emailEditText = findViewById(R.id.startdateEditText);
        contactEditText = findViewById(R.id.enddateEditText);
        dobEditText = findViewById(R.id.salaryEditText);
        orgEditText = findViewById(R.id.orgEditText);
        orgEditText.setFocusable(false);
        orgEditText.setClickable(false);

        int employer_id = getIntent().getIntExtra("employer_id", 1);
        toolbar = findViewById(R.id.toolBarEmployee);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerEmployerLayout);
        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_employer_profile:
                        item.setChecked(true);
                        Intent myIntent = new Intent(EmployerProfile.this, EmployerProfile.class);
                        myIntent.putExtra("employer_id", employer_id);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_employer_post_job:
                        item.setChecked(true);
                        Toast.makeText(EmployerProfile.this, "Profile ", Toast.LENGTH_LONG).show();
                        myIntent = new Intent(EmployerProfile.this, EmployerPostJob.class);
                        myIntent.putExtra("employer_id", employer_id);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_employer_show_jobs:
                        System.out.println(employer_id + "idhar");
                        item.setChecked(true);
                        Toast.makeText(EmployerProfile.this, "Profile ", Toast.LENGTH_LONG).show();
                        myIntent = new Intent(EmployerProfile.this, EmployerApplications.class);
                        myIntent.putExtra("employer_id", employer_id);
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
                .employerDetails(employer_id);
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
                        orgEditText.setText(jobject.get("org").getAsString());
                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployerProfile.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployerProfile.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployerProfile.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployerProfile.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });

    }

    public void updateClicked(View v){

    }
}
