@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.view

import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.personalization.objects.Objects
import com.example.myapplication.R
import com.example.myapplication.personalization.Empleados
import com.example.myapplication.databinding.ActivityDepartamentoBinding
import com.example.myapplication.entity.entity.Departamento
import com.example.myapplication.personalization.Departamentos
import com.example.myapplication.personalization.objects.AdapterListenerDepartamento
import kotlinx.coroutines.launch
import java.util.Calendar

class DepartamentoView : AppCompatActivity(), AdapterListenerDepartamento {

    lateinit var update: ActivityDepartamentoBinding

    var listaDepartamentos: MutableList<Departamento> = mutableListOf()

    lateinit var adaptador: Departamentos

    lateinit var db: Objects

    lateinit var departamento: Departamento


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        update = ActivityDepartamentoBinding.inflate(layoutInflater)
        setContentView(update.root)

        update.rvDepartamentos.layoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(this, Objects::class.java, "dbPruebas").build()

        obtenerDepartamentos(db)

        setTitle(R.string.title_menu)

        update.btnAddUpdate.setOnClickListener {

            if (update.etNombre.text.isNullOrEmpty() ||
                update.swDisponiblilidad.text.isNullOrEmpty() ||
                update.etPrecio.text.isNullOrEmpty() ||
                update.etCalificacion.text.isNullOrEmpty()
            ) {
                Toast.makeText(this, "DEBES LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (update.btnAddUpdate.text.equals("agregar")) {
                departamento = Departamento(
                    listaDepartamentos.size + 1,
                    update.etNombre.text.toString().trim(),
                    update.swDisponiblilidad.isChecked,
                    update.etPrecio.text.toString().trim().toDouble(),
                    update.etCalificacion.text.toString().trim().toDouble(),
                    obtenerFechaSeleccionada(update.etFechaAdicion)

                )
                agregarDepartamento(db, departamento)
            } else if (update.btnAddUpdate.text.equals("actualizar")) {
                departamento.nombre = update.etNombre.text.toString().trim()
                departamento.disponible = update.swDisponiblilidad.isChecked // Utiliza isChecked para obtener el estado del Switch
                departamento.precio = update.etPrecio.text.toString().trim().toDouble()
                departamento.calificacion = update.etCalificacion.text.toString().trim().toDouble()
                departamento.fechaAdicion = obtenerFechaSeleccionada(update.etFechaAdicion)

                actualizarDepartamento(db, departamento)
            }
        }

    }

    private fun obtenerFechaSeleccionada(datePicker: DatePicker): String {
        val year = datePicker.year
        val month = datePicker.month + 1 // Los meses en DatePicker van de 0 a 11
        val day = datePicker.dayOfMonth

        return String.format("%04d-%02d-%02d", year, month, day)
    }

    fun obtenerDepartamentos(room: Objects) {
        lifecycleScope.launch {
                listaDepartamentos = room.DepartamentoDAO().getDepartamentos()
            adaptador = Departamentos(listaDepartamentos,this@DepartamentoView)
            update.rvDepartamentos.adapter = adaptador
        }
    }

    fun agregarDepartamento(room: Objects, departamento: Departamento) {
        lifecycleScope.launch {
            room.DepartamentoDAO().crearDepartamento(departamento)
            obtenerDepartamentos(room)
            limpiarCampos()
        }
    }

    fun actualizarDepartamento(room: Objects, departamento: Departamento) {
        lifecycleScope.launch {
            room.DepartamentoDAO().updateDepartamento(departamento)
            obtenerDepartamentos(room)
            limpiarCampos()
        }
    }

    fun limpiarCampos() {
        update.etNombre.setText("")
        update.swDisponiblilidad.isChecked = false
        update.etPrecio.setText("")
        update.etCalificacion.setText("")

        val today = Calendar.getInstance()
        update.etFechaAdicion.updateDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))

        if (update.btnAddUpdate.text.equals("actualizar")) {
            update.btnAddUpdate.setText("agregar")
            //  binding.etNombre.isEnabled = true
        }

    }

    override fun onEditItemClick(departamento: Departamento) {
        update.btnAddUpdate.setText("actualizar")
        //  binding.etNombre.isEnabled = false


        this.departamento = departamento
        update.etNombre.setText(this.departamento.nombre)
        update.swDisponiblilidad.isChecked = this.departamento.disponible
        update.etPrecio.setText(this.departamento.precio.toString())
        update.etCalificacion.setText(this.departamento.calificacion.toString())


        val fechaAdicion = this.departamento.fechaAdicion // Supongamos que es una cadena en formato "YYYY-MM-DD"
        val fechaParts = fechaAdicion.split("-")
        if (fechaParts.size == 3) {
            val year = fechaParts[0].toInt()
            val month = fechaParts[1].toInt() - 1 // Restamos 1 para convertir de 1-12 a 0-11
            val day = fechaParts[2].toInt()
            update.etFechaAdicion.updateDate(year, month, day)
        }

    }

    override fun onDeleteItemClick(departamento: Departamento) {
        lifecycleScope.launch {
            db.DepartamentoDAO().deleteDepartamento(departamento)
            adaptador.notifyDataSetChanged()
            obtenerDepartamentos(db)
        }
    }

}