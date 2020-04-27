package com.example.cs125_spring2020_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static com.example.cs125_spring2020_finalproject.MainActivity.input;

public class display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mIntent = getIntent();
        int hits = mIntent.getIntExtra("totalhit", 0);
        ArrayList<String> list = mIntent.getStringArrayListExtra("list");
        setContentView(R.layout.activity_display);
        TextView display = findViewById(R.id.textView3);
        display.setText("");
        String tostring = "";
        for (int i = 0; i < list.size(); i++) {
            tostring += list.get(i) + "*";
        }
        display.setText(input + String.valueOf(hits) + tostring);
    }
}
