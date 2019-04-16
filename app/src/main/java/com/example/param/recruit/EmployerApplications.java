package com.example.param.recruit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployerApplications extends AppCompatActivity {
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    ListView listView;
    ArrayList<EmployerApplications.applItem> applItems;
    private int employer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_applications);

        drawerLayout = findViewById(R.id.drawerEmployerLayout);
        navigationView = findViewById(R.id.navigationView);

        employer_id = getIntent().getIntExtra("employer_id",0);
        System.out.println(employer_id);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_employer_profile:
                        item.setChecked(true);
                        Intent myIntent = new Intent(EmployerApplications.this, EmployerProfile.class);
                        myIntent.putExtra("employer_id", employer_id);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_employer_post_job:
                        item.setChecked(true);
                        Toast.makeText(EmployerApplications.this, "Profile ", Toast.LENGTH_LONG).show();
                        myIntent = new Intent(EmployerApplications.this, EmployerPostJob.class);
                        myIntent.putExtra("employer_id", employer_id);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_employer_show_jobs:
                        item.setChecked(true);
                        Toast.makeText(EmployerApplications.this, "Profile ", Toast.LENGTH_LONG).show();
                        myIntent = new Intent(EmployerApplications.this, EmployerApplications.class);
                        myIntent.putExtra("employer_id", employer_id);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

        listView = findViewById(R.id.jobListView);
        if(listView == null)
            Log.d("Listview null hai",null);
        EmployerApplications.CustomAdapter customAdapter = new EmployerApplications.CustomAdapter(getApplicationContext(), R.layout.appl_list_item_layout);
        listView.setAdapter(customAdapter);

        applItems = new ArrayList<EmployerApplications.applItem>();

        Call<JsonObject> call2 = RetrofitClient
                .getInstance()
                .getApi()
                .fetchApplicationsEmployer(employer_id);
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
                        JsonArray skillArray = jobject.get("applications").getAsJsonArray();
                        for(int i=0;i<skillArray.size();i++){
                            applItems.add(new EmployerApplications.applItem(
                                    skillArray.get(i).getAsJsonObject().get("employee_id").getAsInt(),
                                    skillArray.get(i).getAsJsonObject().get("job_id").getAsInt(),
                                    skillArray.get(i).getAsJsonObject().get("emplname").getAsString(),
                                    skillArray.get(i).getAsJsonObject().get("sop").getAsString()
                            ));
                        }

                        for(int i=0; i<applItems.size(); i++)
                            customAdapter.add(applItems.get(i));

                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployerApplications.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployerApplications.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployerApplications.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployerApplications.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EmployerApplications.applItem clickedjob= applItems.get(position);
                Intent intent = new Intent(EmployerApplications.this, EmployerApplicationDisplay.class);
                intent.putExtra("job_id", clickedjob.getJobid());
                intent.putExtra("employee_id", clickedjob.getEmployee_id());
                intent.putExtra(("employer_id"), employer_id);
                startActivity(intent);
            }
        });
    }

    class applItem{
        private int employee_id;
        private int jobid;
        private String name;
        private String sop;

        public applItem(){}
        public applItem(int employee_id, int jobid, String name, String sop){
            this.employee_id = employee_id;
            this.jobid = jobid;
            this.name = name;
            this.sop = sop;
        }
        public int getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_id(int employee_id) {
            this.employee_id = employee_id;
        }

        public int getJobid() {
            return jobid;
        }

        public void setJobid(int jobid) {
            this.jobid = jobid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSop() {
            return sop;
        }

        public void setSop(String sop) {
            this.sop = sop;
        }
    }

    class CustomAdapter extends ArrayAdapter<EmployerApplications.applItem> {
        public CustomAdapter(Context context, int resouces){
            super(context, resouces);
        }

        public CustomAdapter(Context context, int resource, List<EmployerApplications.applItem> jobs){ super(context,resource,jobs);}

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            //LayoutHandler layoutHandler;
            if (row == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                row = vi.inflate(R.layout.appl_list_item_layout, null);
            }

            EmployerApplications.applItem p = getItem(position);

            if (p != null) {
                TextView tt1 = (TextView) row.findViewById(R.id.nameTextView);
                TextView tt2 = (TextView) row.findViewById(R.id.sopTextView);

                if (tt1 != null) {
                    tt1.setText(p.getName());
                }

                if (tt2 != null) {
                    tt2.setText(p.getSop());
                }
            }
            return row;
        }
    }
}
