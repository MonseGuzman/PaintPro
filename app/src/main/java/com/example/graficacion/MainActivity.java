package com.example.graficacion;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ListView lvLista;
    ArrayList archivosLista = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvLista = (ListView)findViewById(R.id.lvLista);

        CrearDirectorio("PaintPro");
        cargarLista();

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

    //CARGA EL LISTVIEW
    public void cargarLista()
    {
        File ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //regresa la ruta
        File archivo = new File(ruta.getAbsolutePath() +"/PaintPro");
        File[] nombres = archivo.listFiles();

        if (nombres.length > 0)
        {
            for (int x=0; x< nombres.length; x++)
            {
                if(nombres[x].isFile())
                {
                    archivosLista.add(nombres[x].getName());
                }
            }

            ArrayAdapter<String> adapter  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, archivosLista);
            lvLista.setAdapter(adapter);
        }
        else {
            ArrayAdapter<String> adapter  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, archivosLista);
            lvLista.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        archivosLista.clear();
        cargarLista();

        super.onResume();
    }

    private void CrearDirectorio(String nombre)
    {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nombre);
        if(!file.exists())
            file.mkdirs();//la primera vez crea el directorio
    }

}
