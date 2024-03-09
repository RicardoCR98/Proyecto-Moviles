@file:Suppress("SpellCheckingInspection")

package com.example.myapplication.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.databinding.ActivityEmpleadoBinding
import com.example.myapplication.personalization.objects.Objects
import com.example.myapplication.R
import com.example.myapplication.entity.entity.Empleado
import com.example.myapplication.personalization.Empleados
import com.example.myapplication.personalization.objects.AdapterListenerEmpleado
import kotlinx.coroutines.launch

class EmpleadoView : AppCompatActivity(), AdapterListenerEmpleado {

    lateinit var update: ActivityEmpleadoBinding

    var listaEmpleados: MutableList<Empleado> = mutableListOf()

    lateinit var adaptador: Empleados

    lateinit var db: Objects

    lateinit var empleado: Empleado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        update = ActivityEmpleadoBinding.inflate(layoutInflater)
        setContentView(update.root)

        update.rvEmpleados.layoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(this, Objects::class.java, "dbPruebas").build()

        obtenerEmpleado(db)

        setTitle(R.string.title_comida)

        update.btnAddUpdateEmpleado.setOnClickListener {

            if (update.etNombreEmpleado.text.isNullOrEmpty() ||
                update.etTipoEmpleado.text.isNullOrEmpty() ||
                update.etDescripcionEmpleado.text.isNullOrEmpty() ||
                update.etCaloriasEmpleado.text.isNullOrEmpty()
            ) {
                Toast.makeText(this, "DEBES LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (update.btnAddUpdateEmpleado.text.equals("agregar")) {
                empleado = Empleado(
                    listaEmpleados.size + 1,
                    update.etNombreEmpleado.text.toString().trim(),
                    update.etTipoEmpleado.text.toString().trim(),
                    update.etDescripcionEmpleado.text.toString().trim(),
                    update.etCaloriasEmpleado.text.toString().trim().toDouble(),
                    obtenerDepartamentoId()
                )
                agregarEmpleado(db, empleado)
            } else if (update.btnAddUpdateEmpleado.text.equals("actualizar")) {
                empleado.nombre = update.etNombreEmpleado.text.toString().trim()
                empleado.tipo = update.etTipoEmpleado.text.toString().trim()
                empleado.descripcion = update.etDescripcionEmpleado.text.toString().trim()
                empleado.calorias = update.etCaloriasEmpleado.text.toString().trim().toDouble()
                actualizarEmpleado(db, empleado)
            }
        }

    }

    fun obtenerDepartamentoId(): Int {

        return intent.getIntExtra("menuId", -1)
    }

    fun obtenerEmpleado(room: Objects) {
        lifecycleScope.launch {
         // listaComidas = room.comidaDAO().getComidas()
            listaEmpleados = room.EmpleadoDAO().getEmpleadosByDepartamentoId(obtenerDepartamentoId()).toMutableList()
            adaptador = Empleados(listaEmpleados, this@EmpleadoView)
            update.rvEmpleados.adapter = adaptador
        }
    }

    fun agregarEmpleado(room: Objects, empleado: Empleado) {
        lifecycleScope.launch {
            room.EmpleadoDAO().createEmpleado(empleado)
            obtenerEmpleado(room)
            limpiarCampos()
        }
    }

    fun actualizarEmpleado(room: Objects, empleado: Empleado) {
        lifecycleScope.launch {
            room.EmpleadoDAO().updateEmpleado(empleado)
            obtenerEmpleado(room)
            limpiarCampos()
        }
    }

    fun limpiarCampos() {
        update.etNombreEmpleado.setText("")
        update.etTipoEmpleado.setText("")
        update.etDescripcionEmpleado.setText("")
        update.etCaloriasEmpleado.setText("")
        if (update.btnAddUpdateEmpleado.text.equals("actualizar")) {
            update.btnAddUpdateEmpleado.setText("agregar")
        }
    }

    override fun onEditItemClick(empleado: Empleado) {
        update.btnAddUpdateEmpleado.setText("actualizar")
        this.empleado = empleado
        update.etNombreEmpleado.setText(this.empleado.nombre)
        update.etTipoEmpleado.setText(this.empleado.tipo)
        update.etDescripcionEmpleado.setText(this.empleado.descripcion)
        update.etCaloriasEmpleado.setText(this.empleado.calorias.toString())
    }

    override fun onDeleteItemClick(empleado: Empleado) {
        lifecycleScope.launch {
            db.EmpleadoDAO().deleteEmpleado(empleado.id)
            adaptador.notifyDataSetChanged()
            obtenerEmpleado(db)
            limpiarCampos()
        }
    }

}