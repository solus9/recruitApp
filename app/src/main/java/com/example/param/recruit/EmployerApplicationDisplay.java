package com.example.param.recruit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerApplicationDisplay extends AppCompatActivity {
    EditText organizationEditText, jobtypeEditText, descriptionEditText, startdateEditText, enddateEditText, salaryEditText, locationEditText, skill1EditText, duration1EditText, skill2EditText, duration2EditText;
    EditText statusEditText, sopEditText;
    Button acceptButton, rejectButton;
    private int employee_id, job_id, employer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_application_display);

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
        statusEditText = findViewById(R.id.statusEditText);
        sopEditText = findViewById(R.id.sopEditText);
        acceptButton = findViewById(R.id.acceptButton);
        rejectButton = findViewById(R.id.rejectButton);

        organizationEditText.setClickable(false);
        organizationEditText.setFocusable(false);
        jobtypeEditText.setClickable(false);
        jobtypeEditText.setFocusable(false);
        descriptionEditText.setClickable(false);
        descriptionEditText.setFocusable(false);
        startdateEditText.setClickable(false);
        startdateEditText.setFocusable(false);
        enddateEditText.setClickable(false);
        enddateEditText.setFocusable(false);
        salaryEditText.setClickable(false);
        salaryEditText.setFocusable(false);
        locationEditText.setClickable(false);
        locationEditText.setFocusable(false);
        skill1EditText.setClickable(false);
        skill1EditText.setFocusable(false);
        duration1EditText.setClickable(false);
        duration1EditText.setFocusable(false);
        skill2EditText.setClickable(false);
        skill2EditText.setFocusable(false);
        duration2EditText.setClickable(false);
        duration2EditText.setFocusable(false);
        statusEditText.setClickable(false);
        statusEditText.setFocusable(false);
        sopEditText.setClickable(false);
        sopEditText.setFocusable(false);


        job_id =getIntent().getIntExtra("job_id",0);
        employee_id = getIntent().getIntExtra("employee_id", 0);
        employer_id = getIntent().getIntExtra("employer_id", 0);


        Call<JsonObject> call = RetrofitClient
                .getInstance()
                .getApi()
                .jobDetails(job_id);
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
                        organizationEditText.setText(jobject.get("orgname").getAsString());
                        jobtypeEditText.setText(jobject.get("jobtype").getAsString());
                        descriptionEditText.setText(jobject.get("description").getAsString());
                        startdateEditText.setText(jobject.get("start_date").getAsString());
                        enddateEditText.setText(jobject.get("end_date").getAsString());
                        salaryEditText.setText(jobject.get("salary").getAsString());
                        locationEditText.setText(jobject.get("location").getAsString());
                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployerApplicationDisplay.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployerApplicationDisplay.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployerApplicationDisplay.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployerApplicationDisplay.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });

        Call<JsonObject> call2 = RetrofitClient
                .getInstance()
                .getApi()
                .jobSkills(job_id);
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
                        JsonArray skillArray = jobject.get("skills").getAsJsonArray();
                        if(skillArray.size() >= 1){
                            JsonObject rowElement = skillArray.get(0).getAsJsonObject();
                            skill1EditText.setText(rowElement.get("skill_name").getAsString());
                            duration1EditText.setText(rowElement.get("duration").getAsString());
                        }
                        if(skillArray.size() >= 2){
                            JsonObject rowElement = skillArray.get(1).getAsJsonObject();
                            skill2EditText.setText(rowElement.get("skill_name").getAsString());
                            duration2EditText.setText(rowElement.get("duration").getAsString());
                        }
                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployerApplicationDisplay.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployerApplicationDisplay.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployerApplicationDisplay.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployerApplicationDisplay.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });

        Call<JsonObject> call3 = RetrofitClient
                .getInstance()
                .getApi()
                .appliciationDetails(employee_id, job_id);
        call3.enqueue(new Callback<JsonObject>() {
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
                        sopEditText.setText(jobject.get("sop").getAsString());;
                        int status = jobject.get("status").getAsInt();
                        if(status == 0){
                            statusEditText.setText("Applied");
                            acceptButton.setVisibility(View.VISIBLE);
                            acceptButton.setEnabled(true);
                            acceptButton.setClickable(true);
                            rejectButton.setVisibility(View.VISIBLE);
                            rejectButton.setEnabled(true);
                            rejectButton.setClickable(true);
                        }else if(status == 1){
                            statusEditText.setText("Accepted");
                            acceptButton.setVisibility(View.INVISIBLE);
                            acceptButton.setEnabled(false);
                            acceptButton.setClickable(false);
                            rejectButton.setVisibility(View.VISIBLE);
                            rejectButton.setEnabled(true);
                            rejectButton.setClickable(true);
                        }else{
                            statusEditText.setText("Rejected");
                            acceptButton.setVisibility(View.INVISIBLE);
                            acceptButton.setEnabled(false);
                            acceptButton.setClickable(false);
                            rejectButton.setVisibility(View.INVISIBLE);
                            rejectButton.setEnabled(false);
                            rejectButton.setClickable(false);
                        }
                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployerApplicationDisplay.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployerApplicationDisplay.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployerApplicationDisplay.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployerApplicationDisplay.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });
    }

    public void acceptClicked(View v){
        Call<JsonObject> call2 = RetrofitClient
                .getInstance()
                .getApi()
                .acceptApplication(job_id, employee_id);
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
                        //
                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployerApplicationDisplay.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployerApplicationDisplay.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployerApplicationDisplay.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployerApplicationDisplay.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });
    }

    public void rejectClicked(View v){
        Call<JsonObject> call2 = RetrofitClient
                .getInstance()
                .getApi()
                .rejectApplication(job_id, employee_id);
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
                        //
                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployerApplicationDisplay.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployerApplicationDisplay.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployerApplicationDisplay.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployerApplicationDisplay.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });
    }

}
