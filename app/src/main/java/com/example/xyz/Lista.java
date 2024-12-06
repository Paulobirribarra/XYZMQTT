package com.example.xyz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.FirebaseDatabase;

public class Lista extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    Adapter adapter;

    private Adapter.OnItemClickListener recyclerItemClickListener = new Adapter.OnItemClickListener() {
        @Override
        public void onItemClick(DataClase data) {
            // Cuando se hace clic en un elemento del RecyclerView, inicia la actividad Detalles
            Intent intent = new Intent(Lista.this, Detalles.class);
            intent.putExtra("key", data.getKey());
            // Otros extras si es necesario
            startActivity(intent);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<DataClase>options = new FirebaseRecyclerOptions.Builder<DataClase>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Productos"),DataClase.class)
                .build();

        adapter = new Adapter(options);
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(DataClase data) {
                Intent intent = new Intent(Lista.this, Detalles.class);
                intent.putExtra("key",data.getKey());
                intent.putExtra("nombre", data.getNombre());
                intent.putExtra("tipo", data.getTipo());
                intent.putExtra("ubicacion", data.getUbicación());
                intent.putExtra("urlImagen", data.getImgUrl());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lista.this, Subir.class);
                startActivity(intent);

            }
        });

    }
}