package com.example.cs125_spring2020_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static String textSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button generate = findViewById(R.id.generateButton);
        Button clear = findViewById(R.id.clearButton);
        final EditText search = findViewById(R.id.searchFDA);
        textSearch = search.getText().toString();

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

    public void search() {
        fdaAPI.connect(textSearch, this);
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
