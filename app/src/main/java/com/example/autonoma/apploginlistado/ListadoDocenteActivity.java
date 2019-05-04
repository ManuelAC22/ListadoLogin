package com.example.autonoma.apploginlistado;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListadoDocenteActivity extends AppCompatActivity {

    EditText txtNombreDocente ,txtApellidoDocente;
    Button addDocente;
    ListView listadoDocentes;

    private ArrayList<String> datosDocente;
    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_docente);

        txtNombreDocente = findViewById(R.id.txtNombreDocente);
        txtApellidoDocente = findViewById(R.id.txtApellidoDocente);
        addDocente = findViewById(R.id.addDocente);
        listadoDocentes = findViewById(R.id.listadoDocentes);

        datosDocente = new ArrayList<String>();

        adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datosDocente);
        listadoDocentes.setAdapter(adaptador);

        addDocente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNombreDocente.getText().toString().equals("") && !txtApellidoDocente.getText().toString().equals("")) {
                    datosDocente.add(txtNombreDocente.getText().toString() + "  "+ txtApellidoDocente.getText().toString());
                    adaptador.notifyDataSetChanged();
                    txtNombreDocente.setText("");
                    txtApellidoDocente.setText("");
                }else{
                    Toast.makeText(ListadoDocenteActivity.this, "No puede ingresar un Docente Vacío", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
