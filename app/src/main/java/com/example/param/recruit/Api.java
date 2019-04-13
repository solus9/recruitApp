package com.example.param.recruit;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface Api {

    @FormUrlEncoded
    @POST("login_employee.php")
    Call<JsonObject> loginEmployee(
      @Field("username") String username,
      @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register_employee.php")
    Call<JsonObject> registerEmployee(
            @Field("username") String username,
            @Field("name") String name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("contact") String contact,
            @Field("dob") String dob
    );
}
