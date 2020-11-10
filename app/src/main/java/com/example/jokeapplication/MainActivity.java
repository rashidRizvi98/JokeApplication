package com.example.jokeapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String url;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    RadioButton rGeneral,rProgramming,rKnock;
    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rGeneral=findViewById(R.id.rdGeneral);
        rProgramming=findViewById(R.id.rdProgramming);
        rKnock=findViewById(R.id.rdKnock);




        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems=new ArrayList<>();





    }

    public void onRadioBtnClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        RadioGroup grp=findViewById(R.id.radioGroup);

        switch(view.getId()) {
            case R.id.rdGeneral:
                if (checked)
                    url="https://official-joke-api.appspot.com/jokes/general/ten";
                loadRecyclerViewData();
                    break;
            case R.id.rdProgramming:
                if (checked)
                    url="https://official-joke-api.appspot.com/jokes/programming/ten";
                loadRecyclerViewData();
                break;
            case R.id.rdKnock:
                if (checked)
                    url="https://official-joke-api.appspot.com/jokes/knock-knock/ten";
                loadRecyclerViewData();
                break;

        }




    }



    public void loadRecyclerViewData() {
        List<ListItem> update=new ArrayList<>();
        MyAdapter adapterM=new MyAdapter(listItems,getApplicationContext());
        adapterM.updateData(update);

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();


/*        RadioGroup grp=findViewById(R.id.radioGroup);
        RadioButton answer=findViewById(grp.getCheckedRadioButtonId());

        grp.clearCheck();

        if(answer==rGeneral){
            url="https://official-joke-api.appspot.com/jokes/general/ten";
        }else
        if(answer==rProgramming){
            url="https://official-joke-api.appspot.com/jokes/programming/ten";
        }else
        if(answer==rKnock){
            url="https://official-joke-api.appspot.com/jokes/knock-knock/ten";
        }


*/

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                progressDialog.dismiss();
                for (int i=0; i<response.length(); i++){

                    try {

                        JSONObject jsonObject=response.getJSONObject(i);
                        ListItem item=new ListItem(
                                jsonObject.getString("type"),
                                jsonObject.getString("setup"),
                                jsonObject.getString("punchline")

                        );
                        listItems.add(item);


                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                adapter=new MyAdapter(listItems,getApplicationContext());
                recyclerView.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.btnLogout:{
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }



}