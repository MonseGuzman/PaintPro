package com.example.graficacion;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ListView lvLista;
    private String imagen = "";
    ArrayList archivosLista = new ArrayList();
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvLista = (ListView)findViewById(R.id.lvLista);

        if(!checkPermission())
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imagen = lvLista.getItemAtPosition(i) + ".png";
                enviarImagenEditar(imagen);
            }
        });

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
                    String name = nombres[x].getName().replaceAll(".png", "");
                    archivosLista.add(name);
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
        if(checkPermission()) {
            archivosLista.clear();
            cargarLista();
        }

        super.onResume();
    }

    private void CrearDirectorio(String nombre)
    {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nombre);
        if(!file.exists())
            file.mkdirs();//la primera vez crea el directorio
    }

    public void enviarImagenEditar(String imagen){
        Intent intent = new Intent(this,segundaActivity.class);
        intent.putExtra("imagen", imagen);
        startActivity(intent);
    }

    public boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //Aquí lo que se hace si aceptan el permiso
                    CrearDirectorio("PaintPro");
                    cargarLista();
                }
                else
                    {
                    //Aquí lo que se hace si no lo aceptan
                }
                break;
        }
    }
}
