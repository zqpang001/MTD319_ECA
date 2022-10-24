package com.example.mtd319_tma02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    SharedPreferences pref;
    private String PREFS_NAME = "PrefsFile";
    EditText userNameEditText;
    EditText passwordEditText;
    CheckBox rmbBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userNameEditText = findViewById(R.id.userName_SignIn_TxtField);
        passwordEditText = findViewById(R.id.password_SignIn_TxtField);
        rmbBtn = findViewById(R.id.rememberMe_checkBox);
        pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        getPreferencesData();

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
}