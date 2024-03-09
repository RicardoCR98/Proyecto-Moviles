package com.example.examenib.vista

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.example.examenib.modelo.dao.EmpleadoDAO
@RequiresApi(Build.VERSION_CODES.O)
class ListEmpleadoView : AppCompatActivity() {

    var listaEmpleados = BaseDatosMemoria.listaEmpleado
    private var idItemSeleccionado = 0
    private var idItemEliminar = 0
    private var idDepartamento: Int = -1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_empleado_view)
        idDepartamento = intent.getIntExtra("departamentoId", -1)
        configurarListView()
        val botonNuevo = findViewById<Button>(R.id.btnAgregarEmpleado)
        botonNuevo.setOnClickListener {
            irActividad(CreateEmpleadoView::class.java)
        }
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        intent.putExtra("departamentoId", idDepartamento)
        startActivity(intent)
    }

    private fun configurarListView() {
        val listView = findViewById<ListView>(R.id.lvEmpleado)
        listaEmpleados = EmpleadoDAO().getEmpleadoByDepartamentoId(idDepartamento)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaEmpleados
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    private fun actualizarListView() {
        val listView = findViewById<ListView>(R.id.lvEmpleado)
        listaEmpleados = EmpleadoDAO().getEmpleadoByDepartamentoId(idDepartamento)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaEmpleados
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_editar_empleado -> {
                val empleadoId = idItemSeleccionado
                val intent = Intent(this, UpdateEmpleadoView::class.java)
                intent.putExtra("empleadoId", empleadoId)
                startActivity(intent)
                return true
            }

            R.id.item_eliminar_empleado -> {
                val empleado = listaEmpleados[idItemEliminar]
                val EmpleadoDAO = EmpleadoDAO()
                listaEmpleados.remove(empleado)
                EmpleadoDAO.deleteEmpleado(empleado)
                actualizarListView()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // llenar las opciones del menu (eliminar, editar)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_empleado, menu)

        // obtener el id del ArrayList seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val empleadoId = listaEmpleados[info.position].idEmployee
        val empleadoIdDelete = info.id.toInt()
        idItemSeleccionado = empleadoId
        idItemEliminar = empleadoIdDelete
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