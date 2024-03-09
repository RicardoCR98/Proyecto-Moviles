@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.entity.Departamento

@Dao
interface DepartamentoDAO {

    @Query("SELECT * FROM departamentos")
    suspend fun getDepartamentos(): MutableList<Departamento>

    @Insert
    suspend fun crearDepartamento(departamento: Departamento)

    @Update
    suspend fun updateDepartamento(departamento: Departamento)

    @Delete
    suspend fun deleteDepartamento(departamento: Departamento)

    @Query("SELECT * FROM departamentos WHERE id = :departamentoId")
    suspend fun getDepartamentoById(departamentoId: Int): Departamento
}