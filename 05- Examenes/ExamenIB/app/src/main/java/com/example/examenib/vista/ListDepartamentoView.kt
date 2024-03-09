package com.example.examenib.vista

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.example.examenib.R
import com.example.examenib.almacenamiento.BaseDatosMemoria
import com.example.examenib.modelo.dao.DepartamentoDAO
import com.example.examenib.modelo.entity.Departamento

class ListDepartamentoView : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    val listaDepartamentos = BaseDatosMemoria.listaDepartamento
    private var idItemSeleccionado = 0
    private var idItemEliminar = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_departamento)
        configurarListView()
        val botonNuevo = findViewById<Button>(R.id.btnAgregarDepto)
        botonNuevo.setOnClickListener {
            irActividad(CreateDepartamentoView::class.java)
        }

    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun configurarListView() {
        val listView = findViewById<ListView>(R.id.lvDepartamento)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaDepartamentos
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun actualizarListView() {
        val listView = findViewById<ListView>(R.id.lvDepartamento)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaDepartamentos
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_edit_dptos -> {
                val departamentoId = idItemSeleccionado // Tu ID específico
                val intent = Intent(this, UpdateDepartamentoView::class.java)
                intent.putExtra("departamentoId", departamentoId)
                startActivity(intent)
                return true
            }

            R.id.item_eliminar -> {
                val depto = listaDepartamentos[idItemEliminar]
                val departamentoDAO = DepartamentoDAO()
                departamentoDAO.deleteDepartamento(depto)
                actualizarListView()
                return true
            }

            R.id.item_ver -> {
                val departamentoId = idItemSeleccionado // Tu ID específico
                val intent = Intent(this, ListEmpleadoView::class.java)
                intent.putExtra("departamentoId", departamentoId)
                startActivity(intent)
                return true
            }

            else -> super.onContextItemSelected(item)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        // obtener el id del ArrayList seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val deptoId = listaDepartamentos[info.position].idDepto
        val deptoIdDelete = info.id.toInt()
        // Guardar el ID en una variable para usarlo en otros métodos
        idItemSeleccionado = deptoId
        idItemEliminar = deptoIdDelete
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRestart() {
        super.onRestart()
        actualizarListView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        actualizarListView()
    }
}