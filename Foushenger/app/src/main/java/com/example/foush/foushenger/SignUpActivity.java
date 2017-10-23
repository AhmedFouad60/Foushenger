package com.example.foush.foushenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foush.foushenger.Utils.Session;
import com.example.foush.foushenger.models.MainResponse;
import com.example.foush.foushenger.models.User;
import com.example.foush.foushenger.webservices.API;
import com.example.foush.foushenger.webservices.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    EditText userNameEditText,passwordEditText,emailEditText,adressEditText;
    TextView link_login;
    Button registerBtn;
    public static User user;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userNameEditText=(EditText)findViewById(R.id.input_name);
        passwordEditText=(EditText)findViewById(R.id.input_password);
        emailEditText=(EditText)findViewById(R.id.input_email);
        link_login=(TextView)findViewById(R.id.link_login);


        registerBtn =(Button)findViewById(R.id.btn_signup);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=userNameEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                String email=emailEditText.getText().toString();

//make object of the user
                 user=new User();
                user.username=userName;
                user.email=email;
                user.password=password;

                //Retrofit part to send request
                API api= ServiceGenerator.createService(API.class);
                Call<MainResponse> call=api.registerUser(user);

                call.enqueue(new Callback<MainResponse>() {
                    @Override
                    public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                        if(response.body().status == "2"){ //emil exists
                            Toast.makeText(SignUpActivity.this,response.body().message, Toast.LENGTH_SHORT).show();

                        }else if(response.body().status == "1"){//register ok
                            Toast.makeText(SignUpActivity.this,response.body().message, Toast.LENGTH_SHORT).show();
                            Session.getInstance().loginUser(user);
                            Intent intent=new Intent(SignUpActivity.this,SignInActivity.class);

                            startActivity(intent);

                        }else {//register error
                            Toast.makeText(SignUpActivity.this,response.body().message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MainResponse> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, "unknown problem", Toast.LENGTH_SHORT).show();
                    }
                });








            }//End of onclick



        });//ENd of the listener

link_login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(SignUpActivity.this,SignInActivity.class);
        startActivity(intent);
    }
});



    }//End of the OnCreate




}//End of the SignUpActivity