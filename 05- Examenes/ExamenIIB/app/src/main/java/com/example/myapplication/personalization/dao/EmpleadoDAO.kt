@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.entity.Empleado

@Dao
interface EmpleadoDAO {

    @Query("SELECT * FROM empleados")
    suspend fun getEmpleados(): MutableList<Empleado>

    @Insert
    suspend fun createEmpleado(empleado: Empleado)

    @Update
    suspend fun updateEmpleado(empleado: Empleado)

    @Query("DELETE FROM empleados WHERE id=:id")
    suspend fun deleteEmpleado(id: Int)

    @Query("SELECT * FROM empleados WHERE departamentoId = :departamentoId")
    suspend fun getEmpleadosByDepartamentoId(departamentoId: Int): List<Empleado>

}