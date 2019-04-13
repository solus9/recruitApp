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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSearch extends AppCompatActivity {
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    ListView listView;
    ArrayList<jobItem> jobItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_search);

        drawerLayout = findViewById(R.id.drawerEmployeeLayout);
        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_employee_profile:
                        item.setChecked(true);
                        Intent myIntent = new Intent(EmployeeSearch.this, EmployeeProfile.class);
                        startActivity(myIntent);
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_employee_search:
                        item.setChecked(true);
                        Toast.makeText(EmployeeSearch.this, "Profile ", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

        listView = findViewById(R.id.jobListView);
        if(listView == null)
            Log.d("Listview null hai",null);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), R.layout.job_list_item_layout);
        listView.setAdapter(customAdapter);

        jobItems = new ArrayList<jobItem>();
        jobItems.add(new jobItem("Appple", "Intern", "Nothing"));
        jobItems.add(new jobItem("Microsoft", "SDE", "No"));
        for(int i=0; i<jobItems.size(); i++)
            customAdapter.add(jobItems.get(i));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jobItem clickedjob= jobItems.get(position);
                System.out.println("not invoked");
                Toast.makeText(EmployeeSearch.this, clickedjob.getDes(), Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getApplicationContext(),EditWordActivity.class);
//                intent.putExtra("id_kgp", clickedword.get_id());
//                startActivity(intent);
            }
        });
    }

    String[] names =  {"param", "mangal"};
    String[] jobType =  {"param", "mangal"};
    String[] description = {"saf", "lkfj"};

    class jobItem{
        private String org;
        private String typ;
        private String des;
        public jobItem(){}
        public jobItem(String _org, String _typ, String _des){
            this.org = _org;
            this.typ = _typ;
            this.des = _des;

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
    }

    class CustomAdapter extends ArrayAdapter<jobItem> {
        public CustomAdapter(Context context, int resouces){
            super(context, resouces);
        }

        public CustomAdapter(Context context, int resource, List<jobItem> jobs){ super(context,resource,jobs);}

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            //LayoutHandler layoutHandler;
            if (row == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                row = vi.inflate(R.layout.job_list_item_layout, null);
            }

            jobItem p = getItem(position);

            if (p != null) {
                TextView tt1 = (TextView) row.findViewById(R.id.organizationTextView);
                TextView tt2 = (TextView) row.findViewById(R.id.jobTypeTextView);
                TextView tt3 = (TextView) row.findViewById(R.id.descriptionTextView);

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
