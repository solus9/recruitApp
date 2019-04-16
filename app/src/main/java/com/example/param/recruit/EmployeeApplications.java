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

public class EmployeeApplications extends AppCompatActivity {
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    ListView listView;
    ArrayList<EmployeeApplications.jobItem> jobItems;
    private int employee_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_applications);

        drawerLayout = findViewById(R.id.drawerEmployeeLayout);
        navigationView = findViewById(R.id.navigationView);

        employee_id = getIntent().getIntExtra("employee_id",0);
        System.out.println(String.format("%d", employee_id));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_employee_profile:
                        item.setChecked(true);
                        Intent myIntent = new Intent(EmployeeApplications.this, EmployeeProfile.class);
                        myIntent.putExtra("employee_id", employee_id);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_employee_search:
                        item.setChecked(true);
                        ///Toast.makeText(EmployeeApplications.this, "Profile ", Toast.LENGTH_LONG).show();
                        myIntent = new Intent(EmployeeApplications.this, EmployeeSearch.class);
                        myIntent.putExtra("employee_id", employee_id);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_employee_application:
                        item.setChecked(true);
                        //Toast.makeText(EmployeeApplications.this, "Profile ", Toast.LENGTH_LONG).show();
                        myIntent = new Intent(EmployeeApplications.this, EmployeeApplications.class);
                        myIntent.putExtra("employee_id", employee_id);
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
        EmployeeApplications.CustomAdapter customAdapter = new EmployeeApplications.CustomAdapter(getApplicationContext(), R.layout.job_list_item_layout);
        listView.setAdapter(customAdapter);

        jobItems = new ArrayList<EmployeeApplications.jobItem>();

        Call<JsonObject> call2 = RetrofitClient
                .getInstance()
                .getApi()
                .fetchApplications(employee_id);
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
                        JsonArray skillArray = jobject.get("jobs").getAsJsonArray();
                        for(int i=0;i<skillArray.size();i++){
                            jobItems.add(new EmployeeApplications.jobItem(
                                    skillArray.get(i).getAsJsonObject().get("job_id").getAsInt(),
                                    skillArray.get(i).getAsJsonObject().get("orgname").getAsString(),
                                    skillArray.get(i).getAsJsonObject().get("job_type").getAsString(),
                                    skillArray.get(i).getAsJsonObject().get("description").getAsString()
                            ));
                        }

                        for(int i=0; i<jobItems.size(); i++)
                            customAdapter.add(jobItems.get(i));

                    }else if(jobject.get("code").getAsInt() == 2){
                        Toast.makeText(EmployeeApplications.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EmployeeApplications.this, "Server error", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(EmployeeApplications.this, "Unknown error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(EmployeeApplications.this, "No network connection", Toast.LENGTH_LONG);
                t.printStackTrace();
            }
        });

        //jobItems.add(new jobItem(1, "Appple", "Intern", "Nothing"));
        //jobItems.add(new jobItem(2, "Microsoft", "SDE", "No"));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EmployeeApplications.jobItem clickedjob= jobItems.get(position);
                System.out.println("not invoked");
                String s = String.format("%d", clickedjob.get_id());
                Toast.makeText(EmployeeApplications.this, s, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EmployeeApplications.this, EmployeeApplicationDisplay.class);
                intent.putExtra("job_id", clickedjob.get_id());
                intent.putExtra("employee_id", employee_id);
                startActivity(intent);
            }
        });
    }

    class jobItem{
        private int jobid;
        private String org;
        private String typ;
        private String des;
        public jobItem(){}
        public jobItem(int _jobid, String _org, String _typ, String _des){
            this.org = _org;
            this.typ = _typ;
            this.des = _des;
            this.jobid = _jobid;
        }

        public String getTyp() {
            return typ;
        }

        public void setTyp(String typ) {
            this.typ = typ;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getOrg() {
            return org;
        }

        public void setOrg(String org) {
            this.org = org;
        }

        public int get_id(){
            return this.jobid;
        }
    }

    class CustomAdapter extends ArrayAdapter<EmployeeApplications.jobItem> {
        public CustomAdapter(Context context, int resouces){
            super(context, resouces);
        }

        public CustomAdapter(Context context, int resource, List<EmployeeApplications.jobItem> jobs){ super(context,resource,jobs);}

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            //LayoutHandler layoutHandler;
            if (row == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                row = vi.inflate(R.layout.job_list_item_layout, null);
            }

            EmployeeApplications.jobItem p = getItem(position);

            if (p != null) {
                TextView tt1 = (TextView) row.findViewById(R.id.nameTextView);
                TextView tt2 = (TextView) row.findViewById(R.id.jobTypeTextView);
                TextView tt3 = (TextView) row.findViewById(R.id.sopTextView);

                if (tt1 != null) {
                    tt1.setText(p.getOrg());
                }

                if (tt2 != null) {
                    tt2.setText(p.getTyp());
                }

                if (tt3 != null) {
                    tt3.setText(p.getDes());
                }
            }

            return row;
        }
    }
}
