package com.example.cs125_spring2020_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import static com.example.cs125_spring2020_finalproject.MainActivity.hit;
import static com.example.cs125_spring2020_finalproject.MainActivity.input;
import static com.example.cs125_spring2020_finalproject.MainActivity.searcheddata;
import static com.example.cs125_spring2020_finalproject.MainActivity.textSearch;

public class display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        TextView display = findViewById(R.id.textView3);
        /**if (searcheddata == null) {
            display.setText(MainActivity.textSearch);
        } else {
            display.setText(searcheddata.toString());
        }*/
        display.setText(input + String.valueOf(hit));
    }
}
