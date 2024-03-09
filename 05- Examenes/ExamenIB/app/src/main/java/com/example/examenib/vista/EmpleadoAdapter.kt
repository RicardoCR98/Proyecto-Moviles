package com.example.examenib.vista

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.examenib.modelo.entity.Empleado

class EmpleadoAdapter(private val context: Context, private val empleados: List<Empleado>) : ArrayAdapter<Empleado>(context, 0, empleados) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_multiple_choice, parent, false)
        }

        val empleadoActual = empleados[position]
        val textView = listItemView!!.findViewById<TextView>(android.R.id.text1)
        textView.text = empleadoActual.name
        return listItemView
    }
}