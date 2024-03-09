package com.example.examenib.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.examenib.modelo.entity.Empleado
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale
import java.util.Date

class SQLiteEmpleado(
    contexto: Context?,
): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
){
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEmpleado =
            """
           CREATE TABLE EMPLEADO(
           id INTEGER PRIMARY KEY AUTOINCREMENT,
           nombre VARCHAR(50),
           posicion VARCHAR(50),
           salario MONEY,
           dateHire DATE,
           active BOOLEAN,
           refrenciaIdEmpleado INTEGER,
           FOREIGN KEY (idDepartamento) REFERENCES DEPARTAMENTO(id)
           ) 
        """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEmpleado)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


    fun crearEmpleado(
        nombre: String,
        posicion: String,
        salario: Double,
        active: Boolean
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("posicion", posicion)
        valoresAGuardar.put("salario", salario)
        valoresAGuardar.put("active", active)

        // Obtener la fecha y hora actuales
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateHire = dateFormat.format(Date())

        // Agregar la fecha y hora actuales a los valores a guardar
        valoresAGuardar.put("dateHire", dateHire)

        val resultadoGuardar = basedatosEscritura.insert(
            "ENTRENADOR",
            null,
            valoresAGuardar
        )
        basedatosEscritura.close()
        return resultadoGuardar != -1L
    }

    fun eliminarEmpleado(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura.delete(
            "ENTRENADOR", // Asegúrate de que el nombre de la tabla sea correcto
            "id=?", // Asegúrate de que el nombre de la columna sea correcto
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }


    fun actualizarEmpleado(
        nombre: String,
        posicion: String,
        salario: Double,
        active: Boolean,
        id: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("posicion", posicion)
        valoresAActualizar.put("salario", salario)
        valoresAActualizar.put("active", active)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "ENTRENADOR", // Asegúrate de que el nombre de la tabla sea correcto
            valoresAActualizar,
            "id=?", // Asegúrate de que el nombre de la columna sea correcto
            parametrosConsultaActualizar
        )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun consultarEmpleadoPorID(id: Int): Empleado {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM EMPLEADO WHERE ID = ?
    """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        val usuarioEncontrado = Empleado(0, "", "", 0.0, LocalDateTime.now(), false)

        if (resultadoConsultaLectura.moveToFirst()) {
            val idEmployee = resultadoConsultaLectura.getInt(0)
            val nombre = resultadoConsultaLectura.getString(1)
            val posicion = resultadoConsultaLectura.getString(2)
            val salario = resultadoConsultaLectura.getDouble(3)
            val dateHireStr = resultadoConsultaLectura.getString(4)
            val active = resultadoConsultaLectura.getInt(5) != 0 // 1 para true, 0 para false

            // Convertir la fecha de String a LocalDateTime
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val dateHire = dateFormat.parse(dateHireStr)?.let { LocalDateTime.parse(it.toString()) }

            usuarioEncontrado.idEmployee = idEmployee
            usuarioEncontrado.name = nombre
            usuarioEncontrado.position = posicion
            usuarioEncontrado.salary = salario
            usuarioEncontrado.dateHire = dateHire ?: LocalDateTime.now()
            usuarioEncontrado.isActive = active
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }





}