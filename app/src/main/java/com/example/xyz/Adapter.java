package com.example.xyz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class Adapter extends FirebaseRecyclerAdapter<DataClase, Adapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(DataClase data);
    }
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public Adapter(@NonNull FirebaseRecyclerOptions<DataClase> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull DataClase model) {
        holder.dataNombre.setText(model.getNombre());
        holder.dataTipo.setText(model.getTipo());
        holder.dataUbic.setText(model.getUbicación());
        String key = getRef(position).getKey();
        model.setKey(key);

        Glide.with(holder.dataImg.getContext())
                .load(model.getImgUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .circleCrop()
                .error(R.drawable.ic_launcher_foreground)
                .timeout(60000)  // Aumenta el timeout a 60 segundos (puedes ajustarlo según tus necesidades)
                .into(holder.dataImg);
        Glide.with(holder.dataImg.getContext());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(model);
                }
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dataNombre, dataTipo, dataUbic;
        ImageView dataImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dataNombre = itemView.findViewById(R.id.recName);
            dataTipo = itemView.findViewById(R.id.recTipo);
            dataUbic = itemView.findViewById(R.id.recUbic);
            dataImg = itemView.findViewById(R.id.recImage);
        }

        public void setData(DataClase model) {
            dataNombre.setText(model.getNombre());
            dataTipo.setText(model.getTipo());
            dataUbic.setText(model.getUbicación());

            // Usar Picasso o cualquier otra biblioteca de carga de imágenes para cargar la imagen desde la URL
            Picasso.get().load(model.getImgUrl()).into(dataImg);
        }
    }
}
