package com.app.travelle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Signup extends AppCompatActivity {
    private Button direct_to_login;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);

        direct_to_login = (Button) findViewById(R.id.login_direct);
        direct_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direct_toLogin();
            }
        });
    }
    public void direct_toLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}