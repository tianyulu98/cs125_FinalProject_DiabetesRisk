package com.example.cs125_spring2020_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //WebAPI url string information.
    private static final String fda = "https://api.nal.usda.gov/fdc/v1/search?";
    private static final String apikey = "api_key=WfBOw7Htobl3oAqVVFbVdvEoDiN6IOpd6K6QeG8t";
    //Total hit results for every search.
    public static int hit = 0;
    //stores the converted webAPI endpoint.
    public static String input;
    //stores the volley request queue.
    private static RequestQueue queue;
    //UI widgets.
    private EditText searchinput;
    private Button searchbutton;
    private Button checkButton;
    //store the contents in search bar.
    public static String textSearch;
    //store searched results in map and list.
    public Map<Integer, String> searchedList = new HashMap<>();
    public ArrayList<String> listtochoose = new ArrayList<String>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button generate = findViewById(R.id.generateButton);
        Button clear = findViewById(R.id.clearButton);
        searchbutton = findViewById(R.id.search);
        searchinput = findViewById(R.id.searchFDA);
        checkButton = findViewById(R.id.checkButton);
        //hitting searchbutton should pop a list of searched food.
        searchbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue = Volley.newRequestQueue(getApplicationContext());
                textSearch = searchinput.getText().toString();
                search(textSearch);
            }
        });
        checkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                display();
            }
        });
        //hitting generate button should generate a report.
        generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                generateCreateDialog();
            }
        });
        //hitting clear button should clear all inputs.
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearButton();
            }
        });
    }


    public void search(String toSearch) {
        input = fda + apikey + "&query=" + toSearch;
        JsonObjectRequest searchRequest = new JsonObjectRequest(Request.Method.GET, input,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listtochoose.clear();
                            hit = response.getInt("totalHits");
                            JSONArray foods = response.getJSONArray("foods");
                            for (int i = 0; i < foods.length(); i++) {
                                JSONObject lista = foods.getJSONObject(i);
                                String description = lista.getString("description");
                                //int fdcid = lista.getInt("fdcId");
                                //searchedList.put(fdcid, description);
                                Log.v("added:", description);
                                listtochoose.add(description);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(searchRequest);
    }

    public void display() {
        Intent intent = new Intent(this, display.class);
        intent.putExtra("totalhit", hit);
        if (listtochoose != null) {
            intent.putExtra("list", listtochoose);
        }
        startActivity(intent);
    }

    public void generateCreateDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Your risk");
        alertDialog.setMessage("a report of risk");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void clearButton() {
        return;
    }
}
