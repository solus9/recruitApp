package com.example.param.recruit;

import android.app.Activity;
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

public class EmployeeRegister extends Activity {
    EditText nameEditText, usernameEditText, passwordEditText, emailEditText, contactEditText, dobEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);
        nameEditText = findViewById(R.id.nameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        contactEditText = findViewById(R.id.contactEditText);
        dobEditText = findViewById(R.id.dobEditText);
    }

    public void registerClicked(View v){
        String name = nameEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String contact = contactEditText.getText().toString();
        String dob = dobEditText.getText().toString();

        Call<JsonObject> call = RetrofitClient
                .getInstance()
                .getApi()
                .registerEmployee(username, name, password, email, contact, dob);
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
                        Toast.makeText(EmployeeRegister.this, "Registered", Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(EmployeeRegister.this, EmployeeLogin.class);
                        startActivity(myIntent);
                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployeeRegister.this, "Username taken", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployeeRegister.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployeeRegister.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployeeRegister.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });
    }
}
