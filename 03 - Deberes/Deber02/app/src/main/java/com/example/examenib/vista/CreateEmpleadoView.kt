package com.example.examenib.vista

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.forEach
import com.example.examenib.R
import com.example.examenib.modelo.dao.DepartamentoDAO
import com.example.examenib.modelo.dao.EmpleadoDAO
import com.example.examenib.modelo.entity.Empleado
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class CreateEmpleadoView : AppCompatActivity() {

    private lateinit var etEmpleadoName:AppCompatEditText
    private lateinit var etEmpleadoPosicion:AppCompatEditText
    private lateinit var etEmpleadoSalario:AppCompatEditText
    private lateinit var swActivo:Switch
    private lateinit var btnGuardarEmpleado:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_empleado_view)

        initComponents()
        initListeners()
    }


    private fun initListeners() {
        btnGuardarEmpleado.setOnClickListener{
            val id = EmpleadoDAO().getAllEmpleados().size + 1
            val nombre =etEmpleadoName.text.toString()
            val posicion = etEmpleadoPosicion.text.toString()
            val salario = etEmpleadoSalario.text.toString().toDouble()
            val activo = swActivo.isChecked
            val fecha = LocalDateTime.now()
            val idDepartamento = intent.getIntExtra("departamentoId", -1)
            if(nombre.isNotEmpty()){
                val emple = Empleado(id,nombre,posicion,salario,fecha,activo,idDepartamento)
                EmpleadoDAO().createEmpleado(emple)
                updateTextView()
            }
            finish()
        }
    }

    private fun initComponents() {
        etEmpleadoName = findViewById(R.id.etEmpleadoName)
        etEmpleadoPosicion = findViewById(R.id.etEmpleadoPosicion)
        etEmpleadoSalario = findViewById(R.id.etEmpleadoSalario)
        swActivo = findViewById(R.id.swActivo)
        btnGuardarEmpleado = findViewById(R.id.btnGuardarEmpleado)
    }

    private fun updateTextView() {
        val sb = StringBuilder()
        for (employee in EmpleadoDAO().getAllEmpleados()) {
            sb.append("Nombre: ${employee.name}\n")
            sb.append("Posicion: ${employee.position}\n")
            sb.append("Salario: $${employee.salary}\n")
            sb.append("Fecha de contratacion: ${employee.dateHire}\n")
            sb.append("¿Empleado Activo?: ${if (employee.isActive) "Sí" else "No"}\n")
        }
    }
}