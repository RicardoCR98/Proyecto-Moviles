package com.example.examenib.modelo.dao

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.examenib.almacenamiento.BaseDatosMemoria
import com.example.examenib.modelo.entity.Empleado

class EmpleadoDAO {

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private var empleadoIdCounter = EmpleadoDAO().getAllEmpleados().size + 1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createEmpleado(empleado: Empleado) {
        empleado.idEmployee = empleadoIdCounter
        BaseDatosMemoria.listaEmpleado.add(empleado)
        empleadoIdCounter++
        // Agregar el empleado al departamento correspondiente
        val departamento = BaseDatosMemoria.listaDepartamento.find { it.idDepto == empleado.refrenciaIdEmpleado }
        departamento?.listEmployees?.add(empleado)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateEmpleado(empleado: Empleado) {
        val index = BaseDatosMemoria.listaEmpleado.indexOfFirst {
            it.idEmployee == empleado.idEmployee
        }
        BaseDatosMemoria.listaEmpleado[index] = empleado
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteEmpleado(empleado: Empleado) {
        BaseDatosMemoria.listaEmpleado.remove(empleado)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllEmpleados(): MutableList<Empleado> {
        return BaseDatosMemoria.listaEmpleado
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getEmpleadoById(id: Int): Empleado? {
        return BaseDatosMemoria.listaEmpleado.find { it.idEmployee == id }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getEmpleadoByDepartamentoId(id: Int): MutableList<Empleado> {
        val departamento = BaseDatosMemoria.listaDepartamento.find { it.idDepto == id }
        return departamento?.listEmployees ?: mutableListOf()
    }

}