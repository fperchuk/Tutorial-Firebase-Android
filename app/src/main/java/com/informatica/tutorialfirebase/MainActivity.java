package com.informatica.tutorialfirebase;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.lista_alumnos)
    RecyclerView ListaAlumnos;

    private FirebaseFirestore db;
    LinearLayoutManager linearLayoutManager;
    private AlumnoAdapter mAdapter;

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
                //Redirigo hacia la pantalla para agregar un nuevo alumno
                Intent intent = new Intent(MainActivity.this, AgregarEditar.class);
                startActivity(intent);
            }
        });
        ButterKnife.bind(this);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        ListaAlumnos.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();
        obtenerListaAlumnos();
    }
    private void obtenerListaAlumnos() {
        db.collection("alumnos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                List<Alumno> alumnos = new ArrayList<>();
                for (DocumentSnapshot document : snapshots) {
                    Alumno alum = document.toObject(Alumno.class);
                    alum.setId(document.getId());
                    alumnos.add(alum);
                }
                mAdapter = new AlumnoAdapter(alumnos, getApplicationContext(), db);
                ListaAlumnos.setAdapter(mAdapter);
            }
        });
    }


}