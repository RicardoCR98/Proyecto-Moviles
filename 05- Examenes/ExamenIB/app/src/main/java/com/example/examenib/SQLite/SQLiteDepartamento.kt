package com.example.examenib.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.examenib.modelo.entity.Departamento
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale
import java.util.Date

class SQLiteDepartamento(
    contexto: Context?
): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaDepartamento =
            """
           CREATE TABLE DEPARTAMENTO(
           idDepto INTEGER PRIMARY KEY AUTOINCREMENT,
           name VARCHAR(50),
           location VARCHAR(50),
           nEmployees INTEGER,
           dateCreate DATE,
           teamRemote BOOLEAN
           ) 
        """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaDepartamento)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearDepartamento(
        name: String,
        location: String,
        nEmployees: Int,
        teamRemote: Boolean
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("name", name)
        valoresAGuardar.put("location", location)
        valoresAGuardar.put("nEmployees", nEmployees)
        valoresAGuardar.put("teamRemote", teamRemote)

        // Obtener la fecha y hora actuales
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateCreate = dateFormat.format(Date())

        // Agregar la fecha y hora actuales a los valores a guardar
        valoresAGuardar.put("dateCreate", dateCreate)

        val resultadoGuardar = basedatosEscritura.insert(
            "DEPARTAMENTO",
            null,
            valoresAGuardar
        )
        basedatosEscritura.close()
        return resultadoGuardar != -1L
    }

    fun eliminarDepartamento(idDepto: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(idDepto.toString())
        val resultadoEliminacion = conexionEscritura.delete(
            "DEPARTAMENTO",
            "idDepto=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return resultadoEliminacion != -1
    }

    fun actualizarDepartamento(
        name: String,
        location: String,
        nEmployees: Int,
        teamRemote: Boolean,
        idDepto: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("name", name)
        valoresAActualizar.put("location", location)
        valoresAActualizar.put("nEmployees", nEmployees)
        valoresAActualizar.put("teamRemote", teamRemote)
        val parametrosConsultaActualizar = arrayOf(idDepto.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "DEPARTAMENTO",
            valoresAActualizar,
            "idDepto=?",
            parametrosConsultaActualizar
        )
        conexionEscritura.close()
        return resultadoActualizacion != -1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun consultarDepartamentoPorID(idDepto: Int): Departamento {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM DEPARTAMENTO WHERE idDepto = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(idDepto.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura
        )

        val departamentoEncontrado = Departamento(0, "", "", 0, LocalDateTime.now(), false, mutableListOf())

        if (resultadoConsultaLectura.moveToFirst()) {
            val id = resultadoConsultaLectura.getInt(0)
            val name = resultadoConsultaLectura.getString(1)
            val location = resultadoConsultaLectura.getString(2)
            val nEmployees = resultadoConsultaLectura.getInt(3)
            val dateCreateStr = resultadoConsultaLectura.getString(4)
            val teamRemote = resultadoConsultaLectura.getInt(5) != 0 // 1 para true, 0 para false

            // Convertir la fecha de String a LocalDateTime
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val dateCreate = dateFormat.parse(dateCreateStr)?.let { LocalDateTime.parse(it.toString()) }

            departamentoEncontrado.idDepto = id
            departamentoEncontrado.name = name
            departamentoEncontrado.location = location
            departamentoEncontrado.nEmployees = nEmployees
            departamentoEncontrado.dateCreate = dateCreate ?: LocalDateTime.now()
            departamentoEncontrado.teamRemote = teamRemote
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return departamentoEncontrado
    }
}
