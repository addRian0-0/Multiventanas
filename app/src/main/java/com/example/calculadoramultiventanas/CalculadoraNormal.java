package com.example.calculadoramultiventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class CalculadoraNormal extends AppCompatActivity {

    Numeros n = new Numeros();
    ArrayList<Operaciones> listaOperacion = new ArrayList<>();

    //Respuesta
    //Visualizar las respuetas
    private TextView respuestaEdit;
    //Variables para realizar la operacio
    private float num1 = 0;
    private float num2 = 0;
    //Oeracion guardar el tipo de operacion (+, -, *, / )
    String operacion = "";
    String texto = "";

    public static final String EXTRA_MESSAGE = "com.example.myfisrtapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora_normal);

        respuestaEdit = findViewById(R.id.respuesta);

        try{

            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("historial.txt")));
            texto  = fin.readLine();
            fin.close();

        }catch(Exception e){
            System.out.println("ERROR LECTURA" + e.getMessage());
        }

    }

    public void verHistorial(View view){

        try{

            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("historial.txt")));
            texto  = fin.readLine();
            Log.i("ddd",texto);

            Intent intent = new Intent(this, Historial.class);
            String message = respuestaEdit.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, texto);
            startActivity(intent);

            fin.close();

        }catch(Exception e){
            System.out.println("ERROR LECTURA" + e.getMessage());
        }

    }

    //Asigna el tipo de operacion ("/") a la variable operacion
    public void dividir(View view){
        try{
            n.setNum1(Float.parseFloat(respuestaEdit.getText().toString()));
            operacion = "/";
            respuestaEdit.setText("0");
        }catch (Exception e){
            n.setNum1(0);
            respuestaEdit.setText("0");
        }
    }

    public void igual(View view){
        try{
            float res = 0.0f;
            //Toma el segundo numero a operar del textView
            n.setNum2(Float.parseFloat(respuestaEdit.getText().toString()));
            //Usa un switch para ver que tipo de operacion es y imprime el resultado en TextView
            switch (operacion){

                case "+":
                    res = n.getNum1() + n.getNum2();
                    respuestaEdit.setText(Float.toString(res));
                    break;

                case "/":
                    if(n.getNum2() == 0 || n.getNum1() == 0){
                        Toast.makeText(this, "No se puede dividir entre 0", Toast.LENGTH_LONG).show();
                    }else{
                        res = n.getNum1() / n.getNum2();
                        respuestaEdit.setText("ERROR");
                    }
                    break;

                case "-":
                    res = n.getNum1() - n.getNum2();
                    respuestaEdit.setText(Float.toString(res));
                    break;

                case "*":
                    res = n.getNum1() * n.getNum2();
                    respuestaEdit.setText(Float.toString(res));
                    break;

                case "%":
                    if(n.getNum2() == 0){
                        Toast.makeText(this, "No se puede sacar modulo de cero", Toast.LENGTH_LONG).show();
                    }else{
                        res = n.getNum1() % n.getNum2();
                        respuestaEdit.setText(Float.toString(res));
                    }
                    break;

                case "pot":
                    if(n.getNum2() < 0){
                        Toast.makeText(this, "No se permiten potencias negativas", Toast.LENGTH_LONG).show();
                    }else{
                        if(n.getNum2() == 0 && n.getNum1() == 0){
                            respuestaEdit.setText("ERROR");
                        }else{
                            try{
                                res = (float) Math.pow(n.getNum1(), n.getNum2());
                                respuestaEdit.setText(Float.toString(res));
                            }catch(Exception e ){
                                respuestaEdit.setText("ERROR");
                                Toast.makeText(this, "Ocurrio un error" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    break;

            }

            try{

                OutputStreamWriter escribir = new OutputStreamWriter(openFileOutput("historial.txt", Context.MODE_PRIVATE));
                texto = texto + Float.toString(n.getNum1()) + " " + operacion + " " + Float.toString(n.getNum2()) + " = " + Float.toString(res) + "|\n";
                escribir.write(texto);
                escribir.close();

            }catch(Exception e){
                System.out.println("ERROR sout "  + e.getMessage());
            }

        }catch(Exception e){
            respuestaEdit.setText("ERROR");
        }
    }

    //Borra el ultimo caracter escrito
    public void borrar(View view){
        try{
            String ops = respuestaEdit.getText().toString();
            ops = ops.substring(0, ops.length()-1);
            respuestaEdit.setText(ops);
        }catch(Exception e){
            respuestaEdit.setText("0");
        }
    }

    //Asigna el tipo de operacion ("pot") a la variable operacion
    public void potencia(View view){
        try{
            n.setNum1(Float.parseFloat(respuestaEdit.getText().toString()));
            operacion = "pot";
            respuestaEdit.setText("0");
        }catch(Exception e){
            respuestaEdit.setText("0");
        }
    }

    public void raiz(View view){
        try{
            if(Float.parseFloat(respuestaEdit.getText().toString()) <= 0){
                Toast.makeText(this, "Solo se puede sacar raiz cuadrarda a numero mayores a 0", Toast.LENGTH_LONG).show();
            }else{
                try{
                    double res = Math.sqrt(Float.parseFloat(respuestaEdit.getText().toString()));
                    respuestaEdit.setText(Double.toString(res));
                }catch (Exception e){
                    Toast.makeText(this, "Ocurrio un error..." + e.getMessage(), Toast.LENGTH_LONG).show();
                    respuestaEdit.setText("ERROR");
                }
            }
        }catch(Exception e){
            respuestaEdit.setText("0");
        }
    }

    public void modulo(View view){
        try{
            n.setNum1(Float.parseFloat(respuestaEdit.getText().toString()));
            operacion = "%";
            respuestaEdit.setText("0");
        }catch(Exception e){
            respuestaEdit.setText("0");
        }
    }

    public void multiplicar(View view){
        try{
            n.setNum1(Float.parseFloat(respuestaEdit.getText().toString()));
            operacion = "*";
            respuestaEdit.setText("0");
        }catch(Exception e){
            respuestaEdit.setText("0");
        }
    }

    public void suma(View view){
        try{
            n.setNum1(Float.parseFloat(respuestaEdit.getText().toString()));
            operacion = "+";
            respuestaEdit.setText("0");
        }catch(Exception e){
            respuestaEdit.setText("0");
        }
    }

    public void resta(View view){
        try{
            n.setNum1(Float.parseFloat(respuestaEdit.getText().toString()));
            operacion = "-";
            respuestaEdit.setText("0");
        }catch(Exception e){
            respuestaEdit.setText("0");
        }
    }

    public void puntito(View view){
        respuestaEdit.setText(respuestaEdit.getText() + ".");
    }

    //Reinicia las variables
    public void clear(View view){
        respuestaEdit.setText("0");
        num1 = 0.0f;
        num2 = 0.0f;
        n.setNum1(0.0f);
        n.setNum2(0.0f);
        operacion = "";
    }

    //Para escribir los numeros
    public void siete(View view){
        try{
            if(respuestaEdit.getText().length() > 0){num1 = Float.parseFloat(respuestaEdit.getText().toString());}
            if(num1 == 0.0f ){respuestaEdit.setText("7");}else{respuestaEdit.setText(respuestaEdit.getText() + "7");}
        }catch (Exception e){
            respuestaEdit.setText("7");
        }
    }

    public void ocho(View view) {
        try{
            if(respuestaEdit.getText().length() > 0){num1 = Float.parseFloat(respuestaEdit.getText().toString());}
            if(num1 == 0.0f ){respuestaEdit.setText("8");}else{respuestaEdit.setText(respuestaEdit.getText() + "8");}
        }catch(Exception e){
            respuestaEdit.setText("8");
        }
    }

    public void nueve(View view) {
        try{
            if(respuestaEdit.getText().length() > 0){num1 = Float.parseFloat(respuestaEdit.getText().toString());}
            if(num1 == 0.0f ){respuestaEdit.setText("9");}else{respuestaEdit.setText(respuestaEdit.getText() + "9");}
        }catch(Exception e){
            respuestaEdit.setText("9");
        }
    }

    public void cuatro(View view) {
        try{
            if(respuestaEdit.getText().length() > 0){num1 = Float.parseFloat(respuestaEdit.getText().toString());}
            if(num1 == 0.0f ){respuestaEdit.setText("4");}else{respuestaEdit.setText(respuestaEdit.getText() + "4");}
        }catch(Exception e){
            respuestaEdit.setText("4");
        }
    }

    public void cinco(View view) {
        try{
            if(respuestaEdit.getText().length() > 0){num1 = Float.parseFloat(respuestaEdit.getText().toString());}
            if(num1 == 0.0f ){respuestaEdit.setText("5");}else{respuestaEdit.setText(respuestaEdit.getText() + "5");}
        }catch (Exception e){
            respuestaEdit.setText("5");
        }
    }

    public void seis(View view) {
        try{
            if(respuestaEdit.getText().length() > 0){num1 = Float.parseFloat(respuestaEdit.getText().toString());}
            if(num1 == 0.0f ){respuestaEdit.setText("6");}else{respuestaEdit.setText(respuestaEdit.getText() + "6");}
        }catch(Exception e){
            respuestaEdit.setText("6");
        }
    }

    public void uno(View view) {
        try{
            if(respuestaEdit.getText().length() > 0){num1 = Float.parseFloat(respuestaEdit.getText().toString());}
            if(num1 == 0.0f ){respuestaEdit.setText("1");}else{respuestaEdit.setText(respuestaEdit.getText() + "1");}
        }catch(Exception e){
            respuestaEdit.setText("1");
        }
    }

    public void dos(View view) {
        try{
            if(respuestaEdit.getText().length() > 0){num1 = Float.parseFloat(respuestaEdit.getText().toString());}
            if(num1 == 0.0f ){respuestaEdit.setText("2");}else{respuestaEdit.setText(respuestaEdit.getText() + "2");}
        }catch(Exception e){
            respuestaEdit.setText("2");
        }
    }

    public void tres(View view) {
        try{
            if(respuestaEdit.getText().length() > 0){num1 = Float.parseFloat(respuestaEdit.getText().toString());}
            if(num1 == 0 ){respuestaEdit.setText("3");}else{respuestaEdit.setText(respuestaEdit.getText() + "3");}
        }catch(Exception e){
            respuestaEdit.setText("3");
        }
    }

    public void cero(View view) {
        try{
            if(respuestaEdit.getText().length() > 0){num1 = Float.parseFloat(respuestaEdit.getText().toString());}
            if(num1 == 0.0f ){respuestaEdit.setText("0");}else{respuestaEdit.setText(respuestaEdit.getText() + "0");}
        }catch(Exception e){
            respuestaEdit.setText("0");
        }
    }
}