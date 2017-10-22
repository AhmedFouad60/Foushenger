package com.example.foush.foushenger.webservices;

import com.example.foush.foushenger.models.MainResponse;
import com.example.foush.foushenger.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by foush on 10/21/17.
 */

public interface API {
    @POST("login-user.php")
    Call<MainResponse> loginUser(@Body User user);
    @POST("register-user.php")
    Call<MainResponse> registerUser(@Body User user);

}
