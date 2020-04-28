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
import android.widget.Toast;

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
    private Button addButton;
    //store the contents in search bar.
    public static String textSearch;

    //store searched results in list.
    public ArrayList<Object> listtochoose = new ArrayList<Object>();
    public String[] list;
    public static fdafood inputfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button generate = findViewById(R.id.generateButton);
        Button clear = findViewById(R.id.clearButton);
        searchbutton = findViewById(R.id.search);
        searchinput = findViewById(R.id.searchFDA);
        checkButton = findViewById(R.id.checkButton);

        //hitting add button should pop up list and add.
        addButton = findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tochoosedialg();
            }
        });

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
                                int fdcid = lista.getInt("fdcId");
                                fdafood tem = new fdafood(fdcid, description);
                                listtochoose.add(tem);
                            }
                            tochoosedialg();
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
        Toast.makeText(this,"search complete",Toast.LENGTH_SHORT).show();
    }

    public void display() {
        Intent intent = new Intent(this, display.class);
        if (listtochoose != null) {
            intent.putExtra("list", listtochoose);
        }
        startActivity(intent);
    }

    //display the list dialog to choose
    public void tochoosedialg() {
        //convert to string array.
        list = new String[listtochoose.size()];
        for (int i = 0; i < listtochoose.size(); i++) {
            fdafood tem = (fdafood) listtochoose.get(i);
            list[i] = tem.getName();
        }

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your food");
        builder.setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item
                inputfood = (fdafood) listtochoose.get(which);
            }
        });
        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
                Log.i("input food is ", inputfood.getName());
            }
        });
        builder.setNegativeButton("Cancel", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void calculation() {
        return;
    }

    public void generateCreateDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Your risk");
        alertDialog.setMessage("the fdaid is " + inputfood.getid());
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
