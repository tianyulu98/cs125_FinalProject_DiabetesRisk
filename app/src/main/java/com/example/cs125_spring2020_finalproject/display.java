package com.example.cs125_spring2020_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;



public class display extends AppCompatActivity {
    private ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent mIntent = getIntent();

        TextView display = findViewById(R.id.textView3);
        display.setTextSize(22);
        list.clear();
        list = mIntent.getStringArrayListExtra("list");
        for (int i=0; i < list.size();i++){
            display.append(list.get(i));
            display.append("\n");
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                //Your intent
                Log.i("back", "back hit");
        }
        return super.onKeyDown(keyCode, event);
    }
}
