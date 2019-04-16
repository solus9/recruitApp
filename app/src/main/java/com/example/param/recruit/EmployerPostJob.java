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

public class EmployerPostJob extends AppCompatActivity {
    EditText organizationEditText, jobtypeEditText, descriptionEditText, startdateEditText, enddateEditText, salaryEditText, locationEditText, skill1EditText, duration1EditText, skill2EditText, duration2EditText;
    int employer_id;
    int org_id;
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_post_job);

        employer_id  = getIntent().getIntExtra("employer_id", 1);

        organizationEditText = findViewById(R.id.orgnameEditText);
        jobtypeEditText = findViewById(R.id.jobtypeEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        startdateEditText = findViewById(R.id.startdateEditText);
        enddateEditText = findViewById(R.id.enddateEditText);
        salaryEditText = findViewById(R.id.salaryEditText);
        locationEditText = findViewById(R.id.locationEditText);
        skill1EditText = findViewById(R.id.skill1EditText);
        duration1EditText = findViewById(R.id.duration1EditText);
        skill2EditText = findViewById(R.id.skill2EditText);
        duration2EditText = findViewById(R.id.duration2EditText);

        organizationEditText.setClickable(false);
        organizationEditText.setFocusable(false);


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
                        Intent myIntent = new Intent(EmployerPostJob.this, EmployerProfile.class);
                        myIntent.putExtra("employer_id", employer_id);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_employer_post_job:
                        item.setChecked(true);
                        Toast.makeText(EmployerPostJob.this, "Profile ", Toast.LENGTH_LONG).show();
                        myIntent = new Intent(EmployerPostJob.this, EmployerPostJob.class);
                        myIntent.putExtra("employer_id", employer_id);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_employer_show_jobs:
                        item.setChecked(true);
                        Toast.makeText(EmployerPostJob.this, "Profile ", Toast.LENGTH_LONG).show();
                        myIntent = new Intent(EmployerPostJob.this, EmployerApplications.class);
                        myIntent.putExtra("employer_id", employer_id);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

        Call<JsonObject> call2 = RetrofitClient
                .getInstance()
                .getApi()
                .orgName(employer_id);
        call2.enqueue(new Callback<JsonObject>() {
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
                        String orgname = jobject.get("orgname").getAsString();
                        organizationEditText.setText(orgname);
                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployerPostJob.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployerPostJob.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployerPostJob.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployerPostJob.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });
    }

    public void postClicked(View v){
        System.out.println(salaryEditText.getText().toString());
        Call<JsonObject> call2 = RetrofitClient
                .getInstance()
                .getApi()
                .postJob(
                        jobtypeEditText.getText().toString(),
                        descriptionEditText.getText().toString(),
                        startdateEditText.getText().toString(),
                        enddateEditText.getText().toString(),
                        Integer.parseInt(salaryEditText.getText().toString()),
                        locationEditText.getText().toString(),
                        employer_id
                );
        call2.enqueue(new Callback<JsonObject>() {
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
//                        Intent myIntent = new Intent(EmployerPostJob.this, EmployerProfile.class);
//                        myIntent.putExtra("employer_id", employer_id);
//                        startActivity(myIntent);
                        int job_id = jobject.get("job_id").getAsInt();
                        skillInsert(job_id);
                    }else{
                        Toast.makeText(EmployerPostJob.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployerPostJob.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployerPostJob.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });


    }

    public void skillInsert(int job_id){
        if(skill1EditText.getText().toString().length() > 0){
            Call<JsonObject> call3 = RetrofitClient
                    .getInstance()
                    .getApi()
                    .addJobSkill(job_id, skill1EditText.getText().toString(), Integer.parseInt(duration1EditText.getText().toString()));
            call3.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response != null) {
                        System.out.println(response.code());
                        String res = response.body().toString();
                        Log.d("OnResponse", res);
                        System.out.println(res);
                        JsonElement jelement = new JsonParser().parse(res);
                        JsonObject jobject = jelement.getAsJsonObject();
                        if (jobject.get("code").getAsInt() == 1) {
                            skillInsert2(job_id);
                        } else {
                            Toast.makeText(EmployerPostJob.this, "Server error", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(EmployerPostJob.this, "Unknown error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(EmployerPostJob.this, "No network connection", Toast.LENGTH_LONG);
                    t.printStackTrace();
                }
            });
        }

    }

    public void skillInsert2(int job_id){
        if(skill2EditText.getText().toString().length() > 0){

            Call<JsonObject> call3 = RetrofitClient
                    .getInstance()
                    .getApi()
                    .addJobSkill(job_id, skill2EditText.getText().toString(), Integer.parseInt(duration2EditText.getText().toString()));
            call3.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response != null) {
                        System.out.println(response.code());
                        String res = response.body().toString();
                        Log.d("OnResponse", res);
                        System.out.println(res);
                        JsonElement jelement = new JsonParser().parse(res);
                        JsonObject jobject = jelement.getAsJsonObject();
                        if (jobject.get("code").getAsInt() == 1) {
                            return;
                        } else {
                            Toast.makeText(EmployerPostJob.this, "Server error", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(EmployerPostJob.this, "Unknown error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(EmployerPostJob.this, "No network connection", Toast.LENGTH_LONG);
                    t.printStackTrace();
                }
            });

        }
    }
}
