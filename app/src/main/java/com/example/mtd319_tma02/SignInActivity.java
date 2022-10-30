package com.example.mtd319_tma02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SignInActivity extends AppCompatActivity {

    SharedPreferences pref;
    private String PREFS_NAME = "PrefsFile";
    EditText userNameEditText;
    EditText passwordEditText;
    CheckBox rmbBtn;
    ProgressBar progressBar;
    TextView progressText;
    int counter = 0;
    public static String usernameSession;

    HashMap<String, String> credentialArrayList = new HashMap<String, String>();

    static ArrayList<ListingItem> listingItemA = new ArrayList<ListingItem>();
    static ListingItem[] listingItemArray;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        SignInActivity.context = getApplicationContext();

        userNameEditText = findViewById(R.id.userName_SignIn_TxtField);
        passwordEditText = findViewById(R.id.password_SignIn_TxtField);
        rmbBtn = findViewById(R.id.rememberMe_checkBox);
        pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        progressBar = findViewById(R.id.progressBar);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);
        getPreferencesData();
        callListingItem();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mtd319-ed05.restdb.io/rest/credential?apikey=6357f014626b9c747864aeeb";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                Gson gson = new Gson();
                Credential[] credentialArray = gson.fromJson(response, Credential[].class);
                for (int i = 0; i < credentialArray.length; i++) {
                    Log.d("suss", "username: " + credentialArray[i].username);
                    credentialArrayList.put(credentialArray[i].username, credentialArray[i].password);
                }
                Log.d("credential list ", credentialArrayList.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);

//        RequestQueue queue2 = Volley.newRequestQueue(this);
//        String url2 = "https://mtd319-ed05.restdb.io/rest/host?apikey=6357f014626b9c747864aeeb";
//        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listingItemA.removeAll(listingItemA);
//                Log.d("response: ", response);
//                Gson gson = new Gson();
//                listingItemArray = gson.fromJson(response, ListingItem[].class);
//                for (int i = 0; i < listingItemArray.length; i++) {
//                    Log.d("listing items: ", "  " + listingItemArray[i].image);
//                    listingItemA.add(listingItemArray[i]);
//                }
//                //                Log.d("Home Activity get from restdb: " , Arrays.toString(listingItemA));
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        queue2.add(stringRequest2);


        rmbBtn.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          if (rmbBtn.isChecked()) {
                                              Boolean isRememberChecked = rmbBtn.isChecked();
                                              SharedPreferences.Editor editor = pref.edit();
                                              editor.putString("pref_name", userNameEditText.getText().toString());
                                              editor.putString("pref_pass", passwordEditText.getText().toString());
                                              editor.putBoolean("pref_check", isRememberChecked);
                                              editor.apply();
                                              Toast.makeText(getApplicationContext(), "Settings have been saved.", Toast.LENGTH_SHORT).show();
                                          } else {
                                              pref.edit().clear().apply();
                                          }
                                      }
                                  }
        );

    }

    private void getPreferencesData() {
        SharedPreferences sPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sPrefs.contains("pref_name")) {
            String userName = sPrefs.getString("pref_name", "not found.");
            userNameEditText.setText(userName.toString());
        }
        if (sPrefs.contains("pref_pass")) {
            String password = sPrefs.getString("pref_pass", "not found.");
            passwordEditText.setText(password.toString());
        }
        if (sPrefs.contains("pref_check")) {
            Boolean b = sPrefs.getBoolean("pref_check", false);
            rmbBtn.setChecked(b);
        }

    }

    public void signUp(View view) {
        Intent intent = new Intent();
        intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void checkSignInCredential(View view) {
        counter = 0;
        progressBar.setVisibility(View.VISIBLE);
        progressText.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (counter <= 100) {
                    progressText.setText("" + counter);
                    progressBar.setProgress(counter);
                    counter++;
                    handler.postDelayed(this, 15);
                } else {
                    handler.removeCallbacks(this);
                    if (credentialArrayList.containsKey(userNameEditText.getText().toString())) {
                        Log.d("checkSignIn", "correct username");
                        if (credentialArrayList.containsValue(passwordEditText.getText().toString())) {
                            usernameSession=userNameEditText.getText().toString();
                            Log.d("checkSignIn", "correct password");
                            Intent intent = new Intent(context, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d("checkSignIn", "wrong password");
                        }
                    } else {
                        Log.d("suss", "wrong username");
                    }
                    progressBar.setVisibility(View.GONE);
                    progressText.setVisibility(View.GONE);

                }
            }
        }, 200);


    }


    public static boolean callListingItem() {
        // for listItem Array

        RequestQueue queue2 = Volley.newRequestQueue(SignInActivity.context);
        String url2 = "https://mtd319-ed05.restdb.io/rest/host?apikey=6357f014626b9c747864aeeb";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listingItemA.removeAll(listingItemA);
                Log.d("response: ", response);
                Gson gson = new Gson();
                listingItemArray = gson.fromJson(response, ListingItem[].class);
                for (int i = 0; i < listingItemArray.length; i++) {
                    Log.d("listing items: ", "  " + listingItemArray[i].image);
                    listingItemA.add(listingItemArray[i]);
                }
                //                Log.d("Home Activity get from restdb: " , Arrays.toString(listingItemA));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue2.add(stringRequest2);
        return true;
    }
}