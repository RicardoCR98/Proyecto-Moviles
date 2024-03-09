@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.entity.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "departamentos")
data class Departamento(
    @PrimaryKey (autoGenerate = true) var id: Int,
    @ColumnInfo(name = "nombre") var nombre: String,
    @ColumnInfo(name = "disponible") var disponible: Boolean,
    @ColumnInfo(name = "precio") var precio: Double,
    @ColumnInfo(name = "calificacion") var calificacion: Double,
    @ColumnInfo(name = "fechaAdicion") var fechaAdicion: String // Almacena LocalDateTime como String
)