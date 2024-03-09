package com.example.examenib.modelo.dao

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.examenib.almacenamiento.BaseDatosMemoria
import com.example.examenib.modelo.entity.Departamento

class DepartamentoDAO {

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private var departamentoIdCounter = DepartamentoDAO().getAllDepartamento().size + 1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createDepartamento(departamento: Departamento) {
        departamento.idDepto = departamentoIdCounter
        BaseDatosMemoria.listaDepartamento.add(departamento)
        departamentoIdCounter++
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateDepartamento(departamento: Departamento) {
        val index = BaseDatosMemoria.listaDepartamento.indexOfFirst {
            it.idDepto == departamento.idDepto
        }
        BaseDatosMemoria.listaDepartamento[index] = departamento
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteDepartamento(departamento: Departamento) {
        BaseDatosMemoria.listaDepartamento.remove(departamento)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllDepartamento(): MutableList<Departamento> {
        return BaseDatosMemoria.listaDepartamento
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDepartamentoById(id: Int): Departamento? {
        return BaseDatosMemoria.listaDepartamento.find { it.idDepto == id }
    }
}