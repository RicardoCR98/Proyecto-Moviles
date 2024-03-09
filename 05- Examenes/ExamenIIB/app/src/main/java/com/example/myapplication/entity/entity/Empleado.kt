@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.entity.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "empleados")
data class Empleado(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "nombre") var nombre: String,
    @ColumnInfo(name = "tipo") var tipo: String,
    @ColumnInfo(name = "descripcion")var descripcion: String,
    @ColumnInfo(name = "calorias")var calorias: Double,
    @ColumnInfo(name = "departamentoId" )var menuId: Int,
)