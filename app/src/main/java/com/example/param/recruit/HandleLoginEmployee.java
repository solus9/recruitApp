package com.example.param.recruit;

import android.content.Context;
import android.content.Intent;

import com.google.gson.JsonObject;

public class HandleLoginEmployee implements  Runnable {
    JsonObject result;
    public void setResult(JsonObject result) {
        this.result = result;
    }

    public void run() {
        System.out.println("Login Activity : " + result.toString());
//        Intent myIntent = new Intent(context, EmployeeLogin.class);

    }
}
