package com.example.graficacion;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mi_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nuevo:
                Intent intent = new Intent(this, segundaActivity.class);
                startActivityForResult(intent, 1);
                return true;
            case R.id.acercaDe:
                final AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
                dialogo.setTitle("PaintPRO");
                dialogo.setMessage("Versión 1.0\n --------------INTEGRANTES--------------\n Andrea Monserrat Guzmán López\n Dalia Valeria Mejía Aguayo\n Carlos Alejandro Buenrostro Ramírez");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogo.setCancelable(true);
                    }
                });
                dialogo.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
