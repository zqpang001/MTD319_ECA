package com.example.mtd319_tma02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    TextView passwordMsg;
    TextView matchPasswordMsg;
    TextView usernameMsg;
    TextView emailMsg;
    TextView agreeMsg;
    CheckBox agreeCheckBox;
    EditText userNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText cPasswordEditText;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        userNameEditText = findViewById(R.id.userName_SignUp_TxtField);
        emailEditText = findViewById(R.id.email_SignUp_TxtField);
        passwordEditText = findViewById(R.id.password_SignUp_TxtField);
        cPasswordEditText = findViewById(R.id.cPassword_SignUp_TxtField);
        agreeCheckBox = findViewById(R.id.agree_CheckBox);
        usernameMsg = findViewById(R.id.usernameMsg);
        emailMsg = findViewById(R.id.emailMsg);
        passwordMsg = findViewById(R.id.passwordMsg);
        matchPasswordMsg = findViewById(R.id.matchPasswordMsg);
        agreeMsg = findViewById(R.id.agreeMsg);
    }

    public void signIn(View view) {
        intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }

    public void clickSignUpBtn(View view) {
        if(userNameEditText.getText().toString().isEmpty()){
            usernameMsg.setVisibility(View.VISIBLE);
        }else{
            usernameMsg.setVisibility(View.INVISIBLE);
        }

        if(emailEditText.getText().toString().isEmpty()){
            emailMsg.setVisibility(View.VISIBLE);
        }else{
            emailMsg.setVisibility(View.INVISIBLE);
        }

        if(!agreeCheckBox.isChecked()){
            agreeMsg.setVisibility(View.VISIBLE);
        }else{
            agreeMsg.setVisibility(View.INVISIBLE);
        }

        if(passwordEditText.getText().toString().isEmpty()){
            passwordMsg.setVisibility(View.VISIBLE);
        }else{
            passwordMsg.setVisibility(View.INVISIBLE);
            if(passwordEditText.getText().toString().equals(cPasswordEditText.getText().toString())) {
                matchPasswordMsg.setVisibility(View.INVISIBLE);
            }else{
                matchPasswordMsg.setVisibility(View.VISIBLE);
            }
        }
        if(passwordEditText.getText().toString().equals(cPasswordEditText.getText().toString()) &&
                agreeCheckBox.isChecked() &&
                !emailEditText.getText().toString().isEmpty() &&
                !userNameEditText.getText().toString().isEmpty()
            ){
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://mtd319-ed05.restdb.io/rest/credential?apikey=6357f014626b9c747864aeeb";
            StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                    response -> Toast.makeText(this,"Sign Up Success",Toast.LENGTH_SHORT).show(),
                    error -> Toast.makeText(this,"Sign Up Error",Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Credential credential = new Credential(userNameEditText.getText().toString(),passwordEditText.getText().toString()
                            ,emailEditText.getText().toString(),null);
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(credential);
                    Map map = gson.fromJson(jsonString, Map.class);
                    Log.d("getMap: ",map.toString());
                    return map;
                }
            };
            queue.add(stringRequest);
            intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
        }
    }
}