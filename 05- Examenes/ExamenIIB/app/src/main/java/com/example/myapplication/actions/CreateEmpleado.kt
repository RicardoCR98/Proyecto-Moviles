package com.example.myapplication.actions

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.entity.entity.Empleado
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateEmpleado : AppCompatActivity() {

    lateinit var empleado: Empleado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamento)


        val botonCrear = findViewById<Button>(R.id.btnAddUpdate)

        botonCrear.setOnClickListener {

            val nombre = findViewById<EditText>(R.id.etNombre).text.toString()
            val tipo = findViewById<EditText>(R.id.etTipoEmpleado).text.toString()
            val descripcion = findViewById<EditText>(R.id.etDescripcionEmpleado).text.toString()
            val calorias = findViewById<EditText>(R.id.etCaloriasEmpleado).text.toString()

            crearEmpleado(
                nombre,
                tipo,
                descripcion,
                calorias.toDouble()
            )
        }
    }

    private fun crearEmpleado(
        nombre: String?,
        tipo: String?,
        descripcion: String?,
        calorias: Double?
    ) {
        val db = Firebase.firestore
        val referenciaEmpleados = db
            .collection("empleados")

        referenciaEmpleados.get().addOnSuccessListener { querySnapshot ->
            val nuevoId = querySnapshot.size() + 1

            val datosEmpleado = hashMapOf(
                "id" to nuevoId,
                "nombre" to nombre,
                "tipo" to tipo,
                "descripcion" to descripcion,
                "calorias" to calorias
            )
            if (nombre != null) {
                referenciaEmpleados.document(nuevoId.toString()).set(datosEmpleado)
            }
            finish()
        }
    }
}
