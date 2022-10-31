package com.example.mtd319_tma02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    TextView textView;
    //    HashMap<String, String> credentialArrayList = new HashMap<String, String>();
    ListingItem[] listingItemArray;
    public static Credential[] credentialArrayList;
    public static Credential credentialArrayCurrentUser;
    EditText usernameTextFieldProf;
    EditText mPhoneTextField;
    EditText emailTextFieldProf;
    TextView usernameTextProf_top;

    private static Context context;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ProfileActivity.context = getApplicationContext();
        getSupportActionBar().setTitle("Profile Detail");


        usernameTextFieldProf = findViewById(R.id.usernameTextFieldProf);
        mPhoneTextField = findViewById(R.id.mPhoneTextField);
        emailTextFieldProf = findViewById(R.id.emailTextFieldProf);
        usernameTextProf_top = findViewById(R.id.usernameTextProf_top);

        getProfileDetail();
        handler.postDelayed(setText, 3000);

        //BottomNavigation
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottomNavigationView);
        //Set Host Selected
        bottomNavigationView.setSelectedItemId(R.id.profile);
        //Bottom navigation selected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.host:
                        startActivity(new Intent(getApplicationContext()
                                ,AddNewHostActivity_Task.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.bag:
                        startActivity(new Intent(getApplicationContext()
                                ,BagActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext()
                                ,ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    public void getProfileDetail() {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://mtd319-ed05.restdb.io/rest/credential?apikey=6357f014626b9c747864aeeb";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                Gson gson = new Gson();
                credentialArrayList = gson.fromJson(response, Credential[].class);
                for (int i = 0; i < credentialArrayList.length; i++) {
                    Log.d("suss", "username: " + credentialArrayList[i].username);
                    if (credentialArrayList[i].username.equals(SignInActivity.usernameSession)) {
                        credentialArrayCurrentUser = new Credential(credentialArrayList[i].username, credentialArrayList[i].password
                                , credentialArrayList[i].email, credentialArrayList[i].mobile);


                    }
                }
                Log.d("credential username", credentialArrayCurrentUser.username);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }


    public Runnable setText = new Runnable() {
        @Override
        public void run() {
            usernameTextProf_top.setText(credentialArrayCurrentUser.username);
            usernameTextFieldProf.setText(credentialArrayCurrentUser.username);
            mPhoneTextField.setText(credentialArrayCurrentUser.mobile);
            emailTextFieldProf.setText(credentialArrayCurrentUser.email);

        }
    };

    public void onClickSaveBtn(View view) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://mtd319-ed05.restdb.io/rest/credential?apikey=6357f014626b9c747864aeeb";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(context, "Save profile detail SUCCESS", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(context, "Save profile detail ERROR", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Credential credentialArrayPost = new  Credential(usernameTextFieldProf.getText().toString(),credentialArrayCurrentUser.password
                        ,emailTextFieldProf.getText().toString(),mPhoneTextField.getText().toString());
                Gson gson = new Gson();
                String jsonString = gson.toJson(credentialArrayPost);
                Map map = gson.fromJson(jsonString, Map.class);
                Log.d("getMap: ", map.toString());
                return map;
            }
        };
        queue.add(stringRequest);
    }

    public void onClickLogOutBtn(View view) {
        Intent intent = new Intent();
        intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
