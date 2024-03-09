@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization.objects

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.personalization.dao.EmpleadoDAO
import com.example.myapplication.personalization.dao.DepartamentoDAO
import com.example.myapplication.entity.entity.Empleado
import com.example.myapplication.entity.entity.Departamento

@Database(
    entities = [Departamento::class,
                Empleado::class],
    version = 2
)
abstract class Objects: RoomDatabase() {
    abstract fun DepartamentoDAO(): DepartamentoDAO
    abstract fun EmpleadoDAO(): EmpleadoDAO
}