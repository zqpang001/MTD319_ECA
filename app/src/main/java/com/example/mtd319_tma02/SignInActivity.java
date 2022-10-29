package com.example.mtd319_tma02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    SharedPreferences pref;
    private String PREFS_NAME = "PrefsFile";
    EditText userNameEditText;
    EditText passwordEditText;
    CheckBox rmbBtn;
    HashMap<String,String> credentialArrayList = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userNameEditText = findViewById(R.id.userName_SignIn_TxtField);
        passwordEditText = findViewById(R.id.password_SignIn_TxtField);
        rmbBtn = findViewById(R.id.rememberMe_checkBox);
        pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        getPreferencesData();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mtd319-ed05.restdb.io/rest/credential?apikey=6357f014626b9c747864aeeb";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ",response);
                Gson gson = new Gson();
                Credential[] credentialArray = gson.fromJson(response,Credential[].class);
                for (int i = 0; i < credentialArray.length; i++){
                    Log.d("suss","username: " + credentialArray[i].username);
                    credentialArrayList.put(credentialArray[i].username,credentialArray[i].password);
                }
                Log.d("credential list " , credentialArrayList.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);


        rmbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rmbBtn.isChecked()) {
                    Boolean isRememberChecked = rmbBtn.isChecked();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("pref_name",userNameEditText.getText().toString());
                    editor.putString("pref_pass",passwordEditText.getText().toString());
                    editor.putBoolean("pref_check",isRememberChecked);
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Settings have been saved.",Toast.LENGTH_SHORT).show();
                }else{
                    pref.edit().clear().apply();
                }
             }
            }
        );

    }

    private void getPreferencesData() {
        SharedPreferences sPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sPrefs.contains("pref_name")){
            String userName = sPrefs.getString("pref_name","not found.");
            userNameEditText.setText(userName.toString());}
        if (sPrefs.contains("pref_pass")){
            String password = sPrefs.getString("pref_pass","not found.");
            passwordEditText.setText(password.toString());
        }
        if (sPrefs.contains("pref_check")){
            Boolean b = sPrefs.getBoolean("pref_check",false);
            rmbBtn.setChecked(b);
        }

    }

    public void signUp(View view) {
        Intent intent = new Intent();
        intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void checkSignInCredential(View view) {
        if(credentialArrayList.containsKey(userNameEditText.getText().toString())) {
            Log.d("checkSignIn", "correct username");
            if(credentialArrayList.containsValue(passwordEditText.getText().toString())) {
                Log.d("checkSignIn", "correct password");
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
            }else{
                Log.d("checkSignIn", "wrong password");
            }
        }else{
            Log.d("suss", "wrong username");
        }
    }
}