@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entity.entity.Empleado
import com.example.myapplication.personalization.objects.AdapterListenerEmpleado

class Empleados(
    val listaempleados: MutableList<Empleado>,
    val listener: AdapterListenerEmpleado
): RecyclerView.Adapter<Empleados.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_empleado, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empleado = listaempleados[position]

        holder.tvNombre.text = empleado.nombre
        holder.tvTipo.text = empleado.tipo
        holder.tvDescripcion.text = empleado.descripcion
        holder.tvCalorias.text = empleado.calorias.toString()

        holder.cvempleado.setOnClickListener {
            listener.onEditItemClick(empleado)
        }

        holder.btnBorrarempleado.setOnClickListener {
            listener.onDeleteItemClick(empleado)
        }

    }

    override fun getItemCount(): Int {
        return listaempleados.size
    }

    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val cvempleado = itemView.findViewById<CardView>(R.id.cvEmpleado)

        val tvNombre = itemView.findViewById<TextView>(R.id.tvnombreEmpleado)
        val tvTipo = itemView.findViewById<TextView>(R.id.tvtipoEmpleado)
        val tvDescripcion = itemView.findViewById<TextView>(R.id.tvdescripcionEmpleado)
        val tvCalorias = itemView.findViewById<TextView>(R.id.tvcaloriasEmpleado)

        val btnBorrarempleado = itemView.findViewById<Button>(R.id.btnBorrarEmpleado)

    }

}
