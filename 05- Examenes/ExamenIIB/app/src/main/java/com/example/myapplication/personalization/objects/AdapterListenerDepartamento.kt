@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization.objects

import com.example.myapplication.entity.entity.Departamento

interface AdapterListenerDepartamento {
    fun onEditItemClick(departamento: Departamento)
    fun onDeleteItemClick(departamento: Departamento)
}