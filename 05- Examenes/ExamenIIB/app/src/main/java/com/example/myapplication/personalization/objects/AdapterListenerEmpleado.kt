@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization.objects

import com.example.myapplication.entity.entity.Empleado

interface AdapterListenerEmpleado {
    fun onEditItemClick(empleado: Empleado)
    fun onDeleteItemClick(empleado: Empleado)
}