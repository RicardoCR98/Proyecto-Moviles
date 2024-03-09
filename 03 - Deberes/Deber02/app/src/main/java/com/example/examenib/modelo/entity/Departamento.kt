package com.example.examenib.modelo.entity
import java.time.LocalDateTime

class Departamento(
    var idDepto: Int,
    var name: String,
    var location: String,
    var nEmployees: Int,
    var dateCreate: LocalDateTime,
    var teamRemote: Boolean,
    var listEmployees: MutableList<Empleado>
) {

    override fun toString(): String {

        val sb = StringBuilder()

        sb.appendLine("Nombre: ${name}")
        sb.appendLine("Localidad: ${location}")
        sb.appendLine("Número de empleados: ${nEmployees}")
        sb.appendLine("Fecha de creación: ${dateCreate}")
        sb.appendLine("¿Equipo remoto?: ${if (teamRemote) "Sí" else "No"}")
        sb.appendLine()
        sb.appendLine("============Empleados============")
        sb.appendLine()
        listEmployees.forEach { sb.appendLine(it.toString()) }
        return sb.toString()

    }
}