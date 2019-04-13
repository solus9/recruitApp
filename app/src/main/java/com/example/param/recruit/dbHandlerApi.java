package com.example.param.recruit;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dbHandlerApi {
    public dbHandlerApi(){
    }

    public void login_employee(final HandleLoginEmployee func, String username, String password){
        JsonObject result = null;
        Call<JsonObject> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginEmployee(username, password);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response != null) {
                    System.out.println(response.code());
                    String res = response.body().toString();
                    Log.d("OnResponse", res);
                    System.out.println(res);
//                    JsonObject jsonObject = new JsonObject();
                    JsonElement jelement = new JsonParser().parse(res);
                    JsonObject  jobject = jelement.getAsJsonObject();
                    func.setResult(jobject);
                    func.run();
                }else{
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("code", 4);  //Unknown error
                    func.setResult(jsonObject);
                    func.run();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("code", 3); // No connection
                func.setResult(jsonObject);
                func.run();
                t.printStackTrace();
            }
        });

    }

}
