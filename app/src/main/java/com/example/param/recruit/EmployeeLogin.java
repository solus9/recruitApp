package com.example.param.recruit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EmployeeLogin extends Activity {
    EditText usernameEditText;
    EditText passwordEditText;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        usernameEditText = findViewById(R.id.usernameTextField);
        passwordEditText = findViewById(R.id.passwordTextField);
    }

    public String postData(String username, String password){
        return "{"
                + "'username' : " + username
                + "'password' : " + password
                + "}";
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public void loginClicked(View v){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        String loginJson = postData(username, password);
        try {
            String response = post("http://localhost/recruitlink/backend/login_employee.php", loginJson);
            System.out.println(response);
        }catch(IOException e){
            e.printStackTrace();
        }

//        JSONObj
//        JsonReader jsonReader = new JsonReader();
//        public static String url_login_employee = "http://localhost/recruitlink/backend/login_employee.php";
//        JSONObject json = null;
//        json.
//        Intent myIntent = new Intent(this, EmployeeLogin.class);
//        startActivity(myIntent);
    }

    public void registerClicked(View V){
        Intent myIntent = new Intent(this, EmployeeRegister.class);
        startActivity(myIntent);
    }
}
