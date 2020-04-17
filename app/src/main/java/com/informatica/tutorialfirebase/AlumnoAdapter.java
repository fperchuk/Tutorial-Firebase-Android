package com.informatica.tutorialfirebase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.ViewHolder> {

    private List<alumno> alumnos;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public AlumnoAdapter(List<alumno> alumnos, Context context, FirebaseFirestore firestoreDB) {
        this.alumnos = alumnos;
        this.context = context;
        this.firestoreDB = firestoreDB;
    }

    @Override
    public AlumnoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new AlumnoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlumnoAdapter.ViewHolder holder, int position) {
        final int itemPosition = position;
        final alumno alum = alumnos.get(itemPosition);
       // progressBar.setVisibility(View.GONE);
        holder.nombre.setText(alum.getNombre());
        holder.division.setText(alum.getDivision());
        holder.calificacion.setText(Integer.toString(alum.getCalificacion()));
        holder.itemView.setOnClickListener(v ->
        {
            Intent intent = new Intent(context, AgregarEditar.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", alum.getId());
            intent.putExtra("nombre", alum.getNombre());
            intent.putExtra("division", alum.getDivision());
            intent.putExtra("calificacion", alum.getCalificacion());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nombre)
        TextView nombre;
        /*@BindView(R.id.image)
        CircleImageView imageView;*/
        @BindView(R.id.division)
        TextView division;
        @BindView(R.id.calificacion)
        TextView calificacion;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}