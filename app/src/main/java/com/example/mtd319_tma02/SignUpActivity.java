package com.example.mtd319_tma02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText userNameEditText;
    EditText passwordEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userNameEditText = findViewById(R.id.userName_SignUp_TxtField);
        passwordEditText = findViewById(R.id.password_SignUp_TxtField);

    }

    public void signIn(View view) {
        Intent intent = new Intent();
        intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }

    public void clickSignUpBtn(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mtd319-ed05.restdb.io/rest/credential?apikey=6357f014626b9c747864aeeb";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                response -> Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Credential credential = new Credential(userNameEditText.getText().toString(),passwordEditText.getText().toString());
//                Map<String, String> params = new HashMap<>();
//                params.put("dasdadsa" ,"sadsada");
                Gson gson = new Gson();
                String jsonString = gson.toJson(credential);
                Map map = gson.fromJson(jsonString, Map.class);
                Log.d("getMap: ",map.toString());
                return map;
            }
        };
        queue.add(stringRequest);
    }
}