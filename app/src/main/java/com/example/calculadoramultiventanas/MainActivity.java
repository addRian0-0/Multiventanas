package com.example.calculadoramultiventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Calculadora1(View v){

        try{

            Intent cal = new Intent(this, CalculadoraNormal.class);
            cal.putExtra(EXTRA_MESSAGE, "New");
            startActivity(cal);

        }catch(Exception e){
            System.out.println("Error al iniciar actividad" + e.getMessage());
        }

    }

    public void Calculadora2(View v){

        try{

            Intent cal = new Intent(this, CalculadoraCientifica.class);
            cal.putExtra(EXTRA_MESSAGE, "New");
            startActivity(cal);

        }catch(Exception e){
            System.out.println("Error al iniciar actividad" + e.getMessage());
        }

    }

}