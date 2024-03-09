package com.example.examenib.modelo.entity
import java.time.LocalDateTime

class Empleado(
    var idEmployee: Int,
    var name: String,
    var position: String,
    var salary: Double,
    var dateHire: LocalDateTime,
    var isActive: Boolean,
    var refrenciaIdEmpleado: Int = -1
) {
    override fun toString(): String {
        val sb = StringBuilder()
        sb.appendLine("Nombre: ${name}")
        sb.appendLine("Posición: ${position}")
        sb.appendLine("Salario: $${salary}")
        sb.appendLine("Fecha de contratación: ${dateHire}")
        sb.appendLine("Activo: ${if(isActive) "Sí" else "No"}")
        return sb.toString()

    }
}