package com.informatica.tutorialfirebase;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgregarEditar extends AppCompatActivity {

    private FirebaseFirestore db;
    String id = ""; //Aca voy a guardar el id del documento en caso de que vaya a editar ese elemento
    String nombre;
    String division;
    int calificacion;

    @BindView(R.id.lblTitulo)
    TextView lblTitulo;
    @BindView(R.id.btnAgregar)
    Button btnAgregar;
    @BindView(R.id.btnEliminar)
    Button btnEliminar;
    @BindView(R.id.txtNombre)
    EditText txtNombre;
    @BindView(R.id.txtDivision)
    EditText txtDivision;
    @BindView(R.id.txtCalificacion)
    EditText txtCalificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_editar);
        ButterKnife.bind(this);
        //Obtengo la instancia de la base de datos
        db = FirebaseFirestore.getInstance();

        //Compruebo si llegaron datos a esta activity.
        // Si llegaron voy a mostrar la pantalla de Editar. Si no llegaron muestro la de crear
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            lblTitulo.setText("Modificar Alumno");
            btnAgregar.setText("EDITAR");
            id = bundle.getString("id");
            txtNombre.setText(bundle.getString("nombre"));
            txtDivision.setText(bundle.getString("division"));
            txtCalificacion.setText(String.valueOf(bundle.getInt("calificacion")));
            btnEliminar.setVisibility(View.VISIBLE);
        }
        else
        {
            lblTitulo.setText("Agregar Alumno");
            btnAgregar.setText("AGREGAR");
            btnEliminar.setVisibility(View.GONE);
        }

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = txtNombre.getText().toString();
                division = txtDivision.getText().toString();
                calificacion = Integer.parseInt(txtCalificacion.getText().toString());
                //Si no tengo un ID quiere decir que es un registro nuevo. Si tengo un ID debo actualizar el existente
                if (id.length() > 0) {
                    ActualizarAlumno(id, nombre, division, calificacion);
                } else {
                    AgregarAlumno(nombre, division, calificacion);
                }
                Intent intent = new Intent(AgregarEditar.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
    public void btnEliminar(View v) {
    AlertDialog confirmacion = new AlertDialog.Builder(AgregarEditar.this)
        .setTitle("Eliminar Alumno")
        .setMessage("¿Estás seguro que deseas borrar el alumno?")
        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                EliminarAlumno(id);
                Intent intent = new Intent(AgregarEditar.this, MainActivity.class);
                startActivity(intent);
            }

        })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
        .show();

    }
    private void ActualizarAlumno(String id, String nombre, String division, int calificacion) {
        Map<String, Object> alumno = (new alumno(id, nombre, division, calificacion)).toMap();

        db.collection("alumnos")
                .document(id)
                .set(alumno)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Alumno actualizado correctamente!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("AgregarAlum", "Error al actualizar el alumno", e);
                        Toast.makeText(getApplicationContext(), "Ocurrió un error al actualizar la información", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void AgregarAlumno(String nombre, String division, int calificacion) {
        Map<String, Object> alumno = new alumno(nombre, division, calificacion).toMap();
        db.collection("alumnos")
                .add(alumno)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Alumno agregado correctamente!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("AgregarAlum", "Error al añadir el alumno", e);
                        Toast.makeText(getApplicationContext(), "Ocurrio un error y no se pudo agregar el alumno", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void EliminarAlumno(String id) {
        db.collection("alumnos")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Alumno borrado exitosamente!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

