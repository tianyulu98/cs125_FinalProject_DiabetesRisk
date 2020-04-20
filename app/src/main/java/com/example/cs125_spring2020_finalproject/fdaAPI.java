package com.example.cs125_spring2020_finalproject;

import org.json.JSONObject;

public class fdaAPI {
    public static final String fda = "https://api.nal.usda.gov/fdc";
    public static final String apikey = "WfBOw7Htobl3oAqVVFbVdvEoDiN6IOpd6K6QeG8t";
    private static final int HTTP_BAD_REQUEST = 400;
    public static JSONObject searcheddata;
    public static void connect(final String url, String key, final String tosearch) {

    }
    public static void main(String[] args ) {
        fdaAPI.connect(fda, apikey, "carrot");
        System.out.println(searcheddata);
        return;
    }
    var
}
