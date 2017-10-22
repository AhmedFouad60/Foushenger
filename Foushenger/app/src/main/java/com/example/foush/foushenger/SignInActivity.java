package com.example.foush.foushenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foush.foushenger.models.MainResponse;
import com.example.foush.foushenger.models.User;
import com.example.foush.foushenger.webservices.API;
import com.example.foush.foushenger.webservices.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    TextView link_signup;
    Button login;
    EditText passwordEditText,emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);




        link_signup=(TextView)findViewById(R.id.link_signup);
        emailEditText=(EditText)findViewById(R.id.input_email);
        passwordEditText=(EditText)findViewById(R.id.input_password);


        login =(Button)findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password=passwordEditText.getText().toString();
                String email=emailEditText.getText().toString();

//make object of the user
                User user=new User();
                user.username="";
                user.email=email;
                user.password=password;

                //Retrofit part to send request
                API api= ServiceGenerator.createService(API.class);
                Call<MainResponse> call=api.loginUser(user);

                call.enqueue(new Callback<MainResponse>() {
                    @Override
                    public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {


                        if(response.body().status == "1"){//register ok
                            Toast.makeText(SignInActivity.this,response.body().message, Toast.LENGTH_SHORT).show();

                        }else {//register error
                            Toast.makeText(SignInActivity.this,response.body().message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MainResponse> call, Throwable t) {
                        Toast.makeText(SignInActivity.this, "unknown problem", Toast.LENGTH_SHORT).show();
                    }
                });








            }//End of onclick



        });//ENd of the listener





        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
