package com.example.cs125_spring2020_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    //WebAPI url string information.
    private static final String fda = "https://api.nal.usda.gov/fdc/v1/search?";
    private static final String apikey = "api_key=WfBOw7Htobl3oAqVVFbVdvEoDiN6IOpd6K6QeG8t";
    private static final int carbohydrateID = 1005;
    //stores the converted webAPI endpoint.
    public static String input;

    //stores the volley request queue.
    private static RequestQueue queue;

    //UI widgets.
    private EditText searchinput;
    private Button searchbutton;
    private Button checkButton;
    private Button clearButton;
    private EditText foodweight;
    private EditText heightwindow;
    private EditText weightwindow;
    //store the contents in search bar.
    public static String textSearch;
    public static double foodamount;
    public static double weight;
    public static double height;
    public static int gender;

    //store searched results in list.
    public ArrayList<Object> listtochoose = new ArrayList<Object>();
    public String[] list;
    public static fdafood inputfood;
    public Map<String, Double> nutrients = new HashMap<>();
    public ArrayList<String> enteredFood = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button generate = findViewById(R.id.generateButton);
        searchbutton = findViewById(R.id.search);
        searchinput = findViewById(R.id.searchFDA);
        checkButton = findViewById(R.id.checkButton);
        clearButton = findViewById(R.id.clearButton);
        foodweight = findViewById(R.id.editText2);
        heightwindow = findViewById(R.id.editText4);
        weightwindow = findViewById(R.id.editText3);
        //initiates the map.
        nutrients.put("carb", 0.0);
        nutrients.put("fat", 0.0);
        nutrients.put("cholesterol", 0.0);

        //set up the spinner.
        Spinner genderselect = findViewById(R.id.gender);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        genderselect.setAdapter(adapter);
        genderselect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = position;
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //hitting searchbutton should pop a list of searched food.
        searchbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queue = Volley.newRequestQueue(getApplicationContext());
                textSearch = searchinput.getText().toString();
                String convert = foodweight.getText().toString();
                if(TextUtils.isEmpty(textSearch)) {
                    searchinput.setError("invalid input");
                    return;
                }
                if (TextUtils.isEmpty(convert)) {
                    foodweight.setError("invalid input");
                    return;
                }
                foodamount = Double.parseDouble(convert);
                Log.v("food amount", String.valueOf(foodamount));
                if (foodamount <= 0) {
                    foodweight.setError("invalid input");
                    return;
                }
                search(textSearch);
            }
        });

        //check button should bring the activity of all inputs.
        checkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                display();
            }
        });

        //hitting generate button should generate a report.
        generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(TextUtils.isEmpty(weightwindow.getText().toString())) {
                    weightwindow.setError("invalid input");
                    return;
                }
                if (TextUtils.isEmpty(heightwindow.getText().toString())) {
                    heightwindow.setError("invalid input");
                }
                weight = Double.parseDouble(weightwindow.getText().toString());
                height = Double.parseDouble(heightwindow.getText().toString());
                generateCreateDialog();
            }
        });

        //hitting clear button should clear all inputs.
        clearButton.setOnClickListener(new View.OnClickListener() {
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
                addNutrient(inputfood, foodamount);
                searchinput.setText("");
            }
        });
        builder.setNegativeButton("Cancel", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addNutrient(final fdafood result, final double amount) {
        RequestQueue second = Volley.newRequestQueue(getApplicationContext());
        String getbyid = "https://api.nal.usda.gov/fdc/v1/" + result.getid() + "?" + apikey;
        JsonObjectRequest searchnutrient = new JsonObjectRequest(Request.Method.GET, getbyid,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray foodNutrients = response.getJSONArray("foodNutrients");
                            for (int i = 0; i < foodNutrients.length(); i++) {
                                JSONObject single = foodNutrients.getJSONObject(i);
                                JSONObject nutrient = single.getJSONObject("nutrient");
                                int id = nutrient.getInt("id");
                                if (id == carbohydrateID) {
                                    double mul = amount / 100;
                                    double now = nutrients.get("carb");
                                    double carbAmount = now + single.getDouble("amount") * mul;
                                    nutrients.put("carb", carbAmount);
                                    enteredFood.add(result.getName());
                                }
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
        second.add(searchnutrient);
        Toast.makeText(this,"Food added",Toast.LENGTH_SHORT).show();
    }

    public void generateCreateDialog() {
        double carb = nutrients.get("carb");
        String diabeterisk = calculate.diabete(carb, weight, height, gender);
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Your risk based on diet today");
        alertDialog.setMessage(diabeterisk);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    //hitting clear will erase everything loaded and everything on screen.
    public void clearButton() {
        nutrients.put("carb", 0.0);
        nutrients.put("fat", 0.0);
        nutrients.put("cholesterol", 0.0);
        enteredFood.clear();
        weightwindow.setText("");
        heightwindow.setText("");
        searchinput.setText("");
        foodweight.setText("");
    }

    //check button should return a list of all inputs.
    public void display() {
        Intent intent = new Intent(this, display.class);
        intent.putExtra("list", enteredFood);
        startActivity(intent);
    }
}
