package com.example.param.recruit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
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


public class EmployeeLogin extends AppCompatActivity{
    EditText usernameEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        usernameEditText = findViewById(R.id.usernameTextField);
        passwordEditText = findViewById(R.id.passwordTextField);
    }

    public void loginClicked(View v) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Call<JsonObject> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginEmployee(username, password);
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
                        Toast.makeText(EmployeeLogin.this, "Valid login", Toast.LENGTH_LONG).show();
                        int employee_id = jobject.get("employee_id").getAsInt();
                        Intent myIntent = new Intent(EmployeeLogin.this, EmployeeProfile.class);
                        myIntent.putExtra("employee_id", employee_id);
                        startActivity(myIntent);
                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployeeLogin.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployeeLogin.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployeeLogin.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployeeLogin.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });
    }



    public void registerClicked(View V){
        Intent myIntent = new Intent(this, EmployeeRegister.class);
        startActivity(myIntent);
    }
}
