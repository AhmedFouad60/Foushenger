package com.example.foush.foushenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.foush.foushenger.Utils.Session;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //check the session

        if(Session.getInstance().isUerLoggedIn()){
            Intent intent=new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
        }else {
            Intent intent=new Intent(SplashActivity.this,SignInActivity.class);
            startActivity(intent);

        }




    }//End of onCreate()
}
