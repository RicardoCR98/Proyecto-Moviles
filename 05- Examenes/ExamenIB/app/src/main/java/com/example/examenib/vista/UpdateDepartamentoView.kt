package com.example.examenib.vista

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.forEach
import com.example.examenib.R
import com.example.examenib.almacenamiento.BaseDatosMemoria
import com.example.examenib.modelo.dao.DepartamentoDAO
import com.example.examenib.modelo.dao.EmpleadoDAO
import com.example.examenib.modelo.entity.Empleado
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class UpdateDepartamentoView : AppCompatActivity() {
    private lateinit var btnGuardarDpto: Button
    private lateinit var etName: AppCompatEditText
    private lateinit var etLocalizacion: AppCompatEditText
    private lateinit var etNEmpleados: AppCompatEditText
    private lateinit var swTeamRemoto: Switch
    private lateinit var lvEmpleadosDpto: ListView
    private lateinit var departamento:DepartamentoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_departamento_view)
        val intent = intent
        val departamentoId = intent.getIntExtra("departamentoId", -1)
        initComponents()
        departamento = DepartamentoDAO()
        val depto = departamento.getDepartamentoById(departamentoId)
        if (depto != null) {
            etName.setText(depto.name)
            etLocalizacion.setText(depto.location)
            etNEmpleados.setText(depto.nEmployees.toString())
            swTeamRemoto.isChecked = depto.teamRemote

            // Esperar a que el ListView se llene
            lvEmpleadosDpto.post {
                for (i in 0 until lvEmpleadosDpto.count) {
                    val empleado = lvEmpleadosDpto.getItemAtPosition(i) as Empleado
                    if (empleado in depto.listEmployees) {
                        lvEmpleadosDpto.setItemChecked(i, true)
                    }
                }
            }
        }
        btnGuardarDpto.setOnClickListener {
            depto?.let {
                it.name = etName.text.toString()
                it.location = etLocalizacion.text.toString()
                it.nEmployees = etNEmpleados.text.toString().toInt()
                it.teamRemote = swTeamRemoto.isChecked

                // Recoger las selecciones del ListView
                val empleadosSeleccionados = mutableListOf<Empleado>()
                for (i in 0 until lvEmpleadosDpto.count) {
                    if (lvEmpleadosDpto.isItemChecked(i)) {
                        empleadosSeleccionados.add(lvEmpleadosDpto.getItemAtPosition(i) as Empleado)
                    }
                }
                it.listEmployees = empleadosSeleccionados

                // Actualizar el departamento
                DepartamentoDAO().updateDepartamento(it)
                updateTextView()
            }
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateTextView() {
        val sb = StringBuilder()
        for (depto in DepartamentoDAO().getAllDepartamento()) {
            sb.append("Nombre: ${depto.name}\n")
            sb.append("Localidad: ${depto.location}\n")
            sb.append("Número de empleados: ${depto.nEmployees}\n")
            sb.append("Fecha de creación: ${depto.dateCreate}\n")
            sb.append("¿Equipo remoto?: ${if (depto.teamRemote) "Sí" else "No"}\n")
            sb.appendLine()
            sb.appendLine("============Empleados============")
            sb.appendLine()
            lvEmpleadosDpto.forEach{ sb.appendLine(it.toString()) }
        }
    }



    private fun initComponents() {
        btnGuardarDpto = findViewById(R.id.btnGuardarDpto)
        etName = findViewById(R.id.etName)
        etLocalizacion = findViewById(R.id.etLocalizacion)
        etNEmpleados = findViewById(R.id.etNEmpleados)
        swTeamRemoto = findViewById(R.id.swTeamRemoto)
        lvEmpleadosDpto = findViewById(R.id.lvEmpleadosDpto)
        // Obtener la lista de todos los empleados
        val listaEmpleados = EmpleadoDAO().getAllEmpleados()

        // Configurar el adaptador
        val adapter = EmpleadoAdapter(this, listaEmpleados)
        lvEmpleadosDpto.adapter = adapter
    }


}