package com.example.cs125_spring2020_finalproject;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class fdaAPI {
    public static final String fda = "https://api.nal.usda.gov/fdc";
    public static final String apikey = "WfBOw7Htobl3oAqVVFbVdvEoDiN6IOpd6K6QeG8t";
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_OK = 200;
    public static JSONObject searcheddata;
    public static void connect(final String tosearch, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

    }
    public static void main(String[] args ) {

        System.out.println(searcheddata);
        return;
    }

}
