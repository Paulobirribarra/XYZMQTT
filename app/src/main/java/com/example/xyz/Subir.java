package com.example.xyz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Subir extends AppCompatActivity {

    private EditText txtNombre, txtTipo, txtUbi, txtUrl;
    private Button btnSave;
    private ImageView uploadImage;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Productos");
    private String KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir);

        txtNombre = findViewById(R.id.txtNombre);
        txtTipo = findViewById(R.id.txtTipo);
        txtUbi = findViewById(R.id.txtUbi);
        txtUrl = findViewById(R.id.txtUrl);
        btnSave = findViewById(R.id.btnSave);
        uploadImage = findViewById(R.id.uploadImage);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
            }
        });
    }
    private void guardarDatos(){
        String nombre = txtNombre.getText().toString().trim();
        String tipo = txtTipo.getText().toString().trim();
        String ubicacion = txtUbi.getText().toString().trim();
        String urlImagen = txtUrl.getText().toString().trim();

        if (!nombre.isEmpty() && !tipo.isEmpty() && !ubicacion.isEmpty()) {
            DataClase data = new DataClase(KEY, nombre, urlImagen, tipo, ubicacion);


            String clave = databaseReference.push().getKey();

            if (clave !=null){
                databaseReference.child(clave).setValue(data)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    limpiarCampos();
                                    finish();
                                    Toast.makeText(Subir.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(Subir.this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                                }
                                }
                            });
                        }
            }else {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
    private void limpiarCampos(){
        txtNombre.setText("");
        txtTipo.setText("");
        txtUbi.setText("");
        txtUrl.setText("");
    }
}