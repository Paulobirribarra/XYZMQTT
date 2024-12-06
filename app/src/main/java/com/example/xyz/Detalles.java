package com.example.xyz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Detalles extends AppCompatActivity {

    EditText detNom,detUbi, detTipo, detUrl;
    Button butEdit, butActualizar, btnEliminar;
    private DatabaseReference databaseReference;
    private String KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        detNom = findViewById(R.id.detalleNombre);
        detUbi = findViewById(R.id.detalleUbi);
        detTipo = findViewById(R.id.detalleTipo);
        detUrl = findViewById(R.id.detalleUrl);
        butEdit = findViewById(R.id.btnEditar);
        butActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        // Obtén la referencia a la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Productos");

        // Obtén los datos pasados desde el RecyclerView
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        String nombre = intent.getStringExtra("nombre");
        String imgUrl = intent.getStringExtra("imgUrl");
        String tipo = intent.getStringExtra("tipo");
        String ubicacion = intent.getStringExtra("ubicacion");

        // Asigna los datos a los EditText
        detNom.setText(nombre);
        detUbi.setText(ubicacion);
        detTipo.setText(tipo);
        detUrl.setText(imgUrl);

        // Guarda la clave para su uso posterior si es necesario
        KEY = key;

        butEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Habilita la edición de los EditText
                detNom.setEnabled(true);
                detUbi.setEnabled(true);
                detTipo.setEnabled(true);
                detUrl.setEnabled(true);
                // Muestra un mensaje o realiza otras acciones según sea necesario
                Toast.makeText(Detalles.this, "Modo edición activado", Toast.LENGTH_SHORT).show();
            }
        });
        butActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene los nuevos valores de los EditText
                String nuevoNombre = detNom.getText().toString();
                String nuevaUbicacion = detUbi.getText().toString();
                String nuevoTipo = detTipo.getText().toString();
                String nuevaUrl = detUrl.getText().toString();

                // Realiza la actualización en la base de datos
                actualizarDatos(KEY, nuevoNombre, nuevaUbicacion, nuevoTipo, nuevaUrl);

                // Muestra un mensaje o realiza otras acciones según sea necesario
                Toast.makeText(Detalles.this, "Registro actualizado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarEliminar();
            }
        });
    }
    public void confirmarEliminar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar eliminación");
        builder.setMessage("¿Estás seguro de que deseas eliminar este registro?");
        builder.setPositiveButton("Sí",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarRegistro();
            }
        });
        builder.setNegativeButton("No",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void eliminarRegistro() {
        if (KEY != null && databaseReference != null) {
            databaseReference.child(KEY).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        dataSnapshot.getRef().removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Detalles.this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                                        finish(); // Cierra la actividad y vuelve a la actividad anterior (Lista)
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Detalles.this, "Error al eliminar el registro", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Detalles", "Error al intentar eliminar el registro", databaseError.toException());
                    Toast.makeText(Detalles.this, "Error al eliminar el registro", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void actualizarDatos(String key, String nuevoNombre, String nuevaUbicacion, String nuevoTipo, String nuevaUrl) {
        if (key == null) {
            Log.e("Detalles", "Error: key es nulo");
            return;
        }

        if (databaseReference == null) {
            Log.e("Detalles", "Error: databaseReference es nulo");
            return;
        }

        DataClase nuevaData = new DataClase(key, nuevoNombre, nuevaUrl, nuevoTipo, nuevaUbicacion);

        // Sobre escribe todos los datos en la clave especificada con la nuevaData
        databaseReference.child(key).setValue(nuevaData);

        Log.d("Detalles", "Datos actualizados en Firebase para key: " + key);
    }



}
