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

@RequiresApi(Build.VERSION_CODES.O)
class UpdateEmpleadoView : AppCompatActivity() {

    private lateinit var btnGuardarEmpleado: Button
    private lateinit var etEmpleadoName: AppCompatEditText
    private lateinit var etEmpleadoPosicion: AppCompatEditText
    private lateinit var etEmpleadoSalario: AppCompatEditText
    private lateinit var swActivo: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_empleado_view)
        val intent = intent
        val empleadoId = intent.getIntExtra(
            "empleadoId",
            -1
        )
        initComponents()
        val empleado = EmpleadoDAO().getEmpleadoById(empleadoId)
        if (empleado != null) {
            etEmpleadoName.setText(empleado.name)
            etEmpleadoPosicion.setText(empleado.position)
            etEmpleadoSalario.setText(empleado.salary.toString())
            swActivo.isChecked = empleado.isActive

        }
        btnGuardarEmpleado.setOnClickListener {
            empleado?.let {
                it.name = etEmpleadoName.text.toString()
                it.position = etEmpleadoPosicion.text.toString()
                it.salary = etEmpleadoSalario.text.toString().toDouble()
                it.isActive = swActivo.isChecked
                EmpleadoDAO().updateEmpleado(it)
                updateTextView()
            }
            finish()
        }
    }

    private fun initComponents() {
        btnGuardarEmpleado = findViewById(R.id.btnGuardarEmpleado)
        etEmpleadoName = findViewById(R.id.etEmpleadoName)
        etEmpleadoPosicion = findViewById(R.id.etEmpleadoPosicion)
        etEmpleadoSalario = findViewById(R.id.etEmpleadoSalario)
        swActivo = findViewById(R.id.swActivo)
    }
    private fun updateTextView() {
        val sb = StringBuilder()
        for (empleado in EmpleadoDAO().getAllEmpleados()) {
            sb.appendLine("Nombre: ${empleado.name}")
            sb.appendLine("Posición: ${empleado.position}")
            sb.appendLine("Salario: $${empleado.salary}")
            sb.appendLine("Fecha de contratación: ${empleado.dateHire}")
            sb.appendLine("Activo: ${if(empleado.isActive) "Sí" else "No"}")
        }
    }

}