@file:Suppress("SpellCheckingInspection")
package com.example.myapplication.personalization

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entity.entity.Departamento
import com.example.myapplication.personalization.objects.AdapterListenerDepartamento
import com.example.myapplication.view.EmpleadoView

class Departamentos(
    val listaDepartamentos: MutableList<Departamento>,
    val listener: AdapterListenerDepartamento
): RecyclerView.Adapter<Departamentos.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_departamento, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val departamento = listaDepartamentos[position]

        holder.tvNombre.text = departamento.nombre
        holder.tvDisponible.text = departamento.disponible.toString()
        holder.tvPrecio.text = departamento.precio.toString()
        holder.tvCalificacion.text = departamento.calificacion.toString()
        holder.tvFechaAdicion.text = departamento.fechaAdicion


        holder.cvdepartamento.setOnClickListener {
            listener.onEditItemClick(departamento)
        }

        holder.btnBorrar.setOnClickListener {
            listener.onDeleteItemClick(departamento)
        }


        holder.btnVerEmpleados.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EmpleadoView::class.java)
            intent.putExtra("departamentoId", departamento.id)
            context.startActivity(intent)
        }

    }


    override fun getItemCount(): Int {
        return listaDepartamentos.size
    }

    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val cvdepartamento = itemView.findViewById<CardView>(R.id.cvDepartamento)
        val tvNombre = itemView.findViewById<TextView>(R.id.tvnombre)
        val tvDisponible = itemView.findViewById<TextView>(R.id.tvdisponible)
        val tvPrecio = itemView.findViewById<TextView>(R.id.tvprecio)
        val tvCalificacion = itemView.findViewById<TextView>(R.id.tvcalificacion)
        val tvFechaAdicion = itemView.findViewById<TextView>(R.id.tvfechaadicion)

        val btnBorrar = itemView.findViewById<Button>(R.id.btnBorrar)
        val btnVerEmpleados = itemView.findViewById<Button>(R.id.btnVerEmpleados)
    }

}