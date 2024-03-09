package com.example.myapplication.entity

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class Departamentos(

    val id: String?,
    val nombre: String?,
    val disponible: Boolean?,
    val precio: Double?,
    val calificacion: Double?,
    val fechaAdicion: Date?
): Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readBoolean(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readValue(Date::class.java.classLoader) as? Date,
        ) {
    }

    override fun toString(): String {
        val format = SimpleDateFormat("dd/MM/yyyy")
        return "$nombre\nPara el ${fechaAdicion?.let { format.format(it) }}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombre)
        parcel.writeString(disponible.toString())
        parcel.writeString(fechaAdicion.toString())
        parcel.writeString(precio.toString())
        parcel.writeString(calificacion.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Departamentos> {

        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Departamentos {
            return Departamentos(parcel)
        }

        override fun newArray(size: Int): Array<Departamentos?> {
            return arrayOfNulls(size)
        }
    }
}