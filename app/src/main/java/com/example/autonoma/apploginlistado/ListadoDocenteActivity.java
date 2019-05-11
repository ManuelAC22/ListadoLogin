package com.example.autonoma.apploginlistado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.autonoma.apploginlistado.adapter.MainAdapter;
import com.example.autonoma.apploginlistado.api.UsuarioAPI;
import com.example.autonoma.apploginlistado.modelo.Usuario;
import com.example.autonoma.apploginlistado.modelo.Usuarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListadoDocenteActivity extends AppCompatActivity {

    EditText txtNombreDocente ,txtApellidoDocente;
    Button addDocente;
    ListView listadoDocentes;

    ArrayList datosDocente;
    ArrayAdapter<String> adaptador;

    Retrofit retrofit;
    UsuarioAPI usuarioApi;

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
        //listadoDocentes.setAdapter(adaptador);

        registerForContextMenu(listadoDocentes);

        addDocente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNombreDocente.getText().toString().equals("") && !txtApellidoDocente.getText().toString().equals("")) {
                    datosDocente.add(txtNombreDocente.getText().toString() + "  "+ txtApellidoDocente.getText().toString());
                    txtNombreDocente.setText("");
                    txtApellidoDocente.setText("");
                    listadoDocentes.deferNotifyDataSetChanged();
                }else{
                    Toast.makeText(ListadoDocenteActivity.this, "No puede ingresar un Docente Vacío", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ///////instanciar el Retrofit
        retrofit = new MainAdapter().getAdapter();
        usuarioApi = retrofit.create(UsuarioAPI.class);

        Call<List<Usuarios>> usuariosCall = usuarioApi.getAllUsuarios();

        usuariosCall.enqueue(new Callback<List<Usuarios>>() {
            @Override
            public void onResponse(Call<List<Usuarios>> call,
                                   Response<List<Usuarios>> response) {
                Log.d("retornoDatos",response.toString());
                Log.d("retornoDatos22",response.body().toString());
                //List<Usuario> usuarios = response.body().getData();
            }

            @Override
            public void onFailure(Call<List<Usuarios>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //
        MenuInflater inflater = getMenuInflater();
        //Adaptador de Vista
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuInfo;
        //
        menu.setHeaderTitle("Profesor:" +
                datosDocente.get(info.position));
        //Llama al menu Creado
        inflater.inflate(R.menu.menu_contextual, menu);

    }// fin metodo onCreateContextMenu

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.menu_eliminar:
                datosDocente.remove(info.position);
                adaptador.notifyDataSetChanged();
                Toast.makeText(this, "Mensaje de Eliminacion", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItem:
                datosDocente.add("Profesor Charles Xavier");
                adaptador.notifyDataSetChanged();
                Toast.makeText(this, "You have selected ADD", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.refresh:
                adaptador.notifyDataSetChanged();
                Toast.makeText(this, "You have selected REFRESH", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.seeMap:
                Intent i = new Intent(ListadoDocenteActivity.this, MapsActivity.class);
                startActivity(i);
                Toast.makeText(this, "You have selected MAP", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.closeApp:
                Toast.makeText(this, "You have selected CLOSE", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListadoDocenteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}