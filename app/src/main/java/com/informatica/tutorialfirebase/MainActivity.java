package com.informatica.tutorialfirebase;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.lista_alumnos)
    RecyclerView ListaAlumnos;

    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ButterKnife.bind(this);
        init();
        obtenerListaAlumnos();
    }
    private void init(){
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        ListaAlumnos.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void obtenerListaAlumnos(){
        //Hago una query para obtener los alumnos guardados en la BD
        Query query = db.collection("alumnos");

        FirestoreRecyclerOptions<alumno> response = new FirestoreRecyclerOptions.Builder<alumno>()
                .setQuery(query, alumno.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<alumno, AlumnosHolder>(response) {
            @Override
            public void onBindViewHolder(AlumnosHolder holder, int position, alumno model) {
                progressBar.setVisibility(View.GONE);
                holder.nombre.setText(model.getNombre());
                holder.division.setText(model.getDivision());
                holder.calificacion.setText(Integer.toString(model.getCalificacion()));
                /*Glide.with(getApplicationContext())
                        .load(model.getImage())
                        .into(holder.imageView);*/
/*
                holder.itemView.setOnClickListener(v -> {

                });*/
            }

            @Override
            public AlumnosHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.list_item, group, false);

                return new AlumnosHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        ListaAlumnos.setAdapter(adapter);
    }

    public class AlumnosHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nombre)
        TextView nombre;
        /*@BindView(R.id.image)
        CircleImageView imageView;*/
        @BindView(R.id.division)
        TextView division;
        @BindView(R.id.calificacion)
        TextView calificacion;

        public AlumnosHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}