package com.example.examenib.vista
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.forEach
import com.example.examenib.R
import com.example.examenib.modelo.dao.DepartamentoDAO
import com.example.examenib.modelo.dao.EmpleadoDAO
import com.example.examenib.modelo.entity.Departamento
import com.example.examenib.modelo.entity.Empleado
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class CreateDepartamentoView : AppCompatActivity() {
    private lateinit var btnGuardarDpto:Button
    private lateinit var etName: AppCompatEditText
    private lateinit var etLocalizacion:AppCompatEditText
    private lateinit var etNEmpleados:AppCompatEditText
    private lateinit var swTeamRemoto:Switch
    private lateinit var lvEmpleadosDpto:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_departamento_view)
        initComponents()
        initListeners()
    }


    private fun initListeners() {
        btnGuardarDpto.setOnClickListener{
            val id = DepartamentoDAO().getAllDepartamento().size +1
            var nombre = etName.text.toString()
            val location = etLocalizacion.text.toString()
            val nEmpleados = etNEmpleados.text.toString().toInt()
            val tRemoto = swTeamRemoto.isChecked
            val fecha = LocalDateTime.now()
            // Obtener las posiciones seleccionadas
            val checked: SparseBooleanArray = lvEmpleadosDpto.checkedItemPositions
            val seleccionados = mutableListOf<Empleado>()
            // Iterar sobre los elementos del ListView
            for (i in 0 until lvEmpleadosDpto.count) {
                if (checked[i]) {
                    // Suponiendo que tu ListView está poblado con objetos Empleado
                    seleccionados.add(lvEmpleadosDpto.getItemAtPosition(i) as Empleado)
                }
            }
            // Crear el departamento con los empleados seleccionados
            if (nombre.isNotEmpty()) {
                val departamento = Departamento(id, nombre, location, nEmpleados, fecha, tRemoto, seleccionados)
                DepartamentoDAO().createDepartamento(departamento)
                updateTextView()
            }
            finish()
        }
    }

    private fun initComponents() {
        btnGuardarDpto = findViewById(R.id.btnGuardarDpto)
        etName = findViewById(R.id.etName)
        etLocalizacion = findViewById(R.id.etLocalizacion)
        etNEmpleados = findViewById(R.id.etNEmpleados)
        swTeamRemoto = findViewById(R.id.swTeamRemoto)
        lvEmpleadosDpto = findViewById(R.id.lvEmpleadosDpto)

        // Obtener la lista de empleados
        val listaEmpleados = EmpleadoDAO().getAllEmpleados()
        // Configurando el adaptador
        val adapter = EmpleadoAdapter(this, listaEmpleados)
        lvEmpleadosDpto.adapter = adapter
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

}

