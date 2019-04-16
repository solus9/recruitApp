package com.example.param.recruit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerRegister extends AppCompatActivity {
    EditText nameEditText, usernameEditText, passwordEditText, emailEditText, contactEditText, dobEditText, orgEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_register);
        nameEditText = findViewById(R.id.orgnameEditText);
        usernameEditText = findViewById(R.id.jobtypeEditText);
        passwordEditText = findViewById(R.id.descriptionEditText);
        emailEditText = findViewById(R.id.startdateEditText);
        contactEditText = findViewById(R.id.enddateEditText);
        dobEditText = findViewById(R.id.salaryEditText);
        orgEditText = findViewById(R.id.orgEditText);

        System.out.println("employer register");
    }

    public void registerClicked(View v){
        String name = nameEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String contact = contactEditText.getText().toString();
        String dob = dobEditText.getText().toString();
        String organization = orgEditText.getText().toString();

        Call<JsonObject> call = RetrofitClient
                .getInstance()
                .getApi()
                .registerEmployer(username, name, password, email, contact, dob, organization);
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
                        Toast.makeText(EmployerRegister.this, "Registered", Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(EmployerRegister.this, EmployerLogin.class);
                        startActivity(myIntent);
                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployerRegister.this, "Username taken", Toast.LENGTH_LONG).show();
                    }else if(jobject.get("code").getAsInt() == 3){
                        Toast.makeText(EmployerRegister.this, "Organization invalid", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployerRegister.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployerRegister.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployerRegister.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }

        });
    }
}
