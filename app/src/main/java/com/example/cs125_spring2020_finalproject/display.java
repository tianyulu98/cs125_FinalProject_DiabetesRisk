package com.example.cs125_spring2020_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static com.example.cs125_spring2020_finalproject.MainActivity.input;

public class display extends AppCompatActivity {
    public String[] tostring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mIntent = getIntent();
        ArrayList<String> list = mIntent.getStringArrayListExtra("list");
        setContentView(R.layout.activity_display);

        TextView display = findViewById(R.id.textView3);
        display.setText("");
        display.setTextSize(22);
        for (int i=0; i < list.size();i++){
            display.append(list.get(i));
            display.append("\n");
        }
    }
}
