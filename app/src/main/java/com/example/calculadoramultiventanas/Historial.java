package com.example.calculadoramultiventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Historial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        Intent intent = getIntent();
        String message = intent.getStringExtra(CalculadoraNormal.EXTRA_MESSAGE);

        TextView his = findViewById(R.id.historialText);

        String Total = "";
        for(int i = 0; i >= message.length()-1; i++){
            if(message.charAt(i) == '|'){
                Total += String.format("\n");
            }else{
                Total += String.format(message.charAt(i) + "");
            }
        }
        his.setText(message);

    }
}