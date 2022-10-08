package com.app.travelle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Login extends AppCompatActivity {
    private Button login_btn;
    private Button direct_to_signup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        login_btn = (Button) findViewById(R.id.login);
        direct_to_signup_btn = (Button) findViewById(R.id.signup);

        direct_to_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direct_toSignUp();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWelcome();
            }
        });
    }
    public void openWelcome(){
        Intent intent = new Intent(this, InitialMap.class);
        startActivity(intent);
    }
    public void direct_toSignUp(){
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

}