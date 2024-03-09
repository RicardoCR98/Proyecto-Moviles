package model.entity

import java.time.LocalDate

class Departamento(
    private val idDepto: Int,
    private val name: String,
    private val location: String,
    private var nEmployees: Int,
    private val dateCreate: LocalDate,
    private var teamRemote: Boolean,
    private val listEmployees: MutableList<Empleado>
) {
    //El constructor se genera de forma implicita, pero se puede añadir un constructor secundario si es necesario
    //Los getters y setters se  generan automáticamente de forma implícita
    fun getidDepto(): Int {
        return idDepto
    }

    fun getName(): String{
        return name
    }
    fun getLocation():String{
        return location
    }
    fun getNEmployees():Int{
        return nEmployees
    }
    fun getDateCreate():LocalDate{
        return dateCreate
    }
    fun getTeamRemote():Boolean{
        return teamRemote
    }

    fun getListEmployees():List<Empleado>{
        return listEmployees
    }
    fun addEmpleado(empleado: Empleado) {
        listEmployees.add(empleado)
    }
    override fun toString(): String {
        val employeeString = listEmployees.joinToString(", ") { it.toString() }
        return "$idDepto, $name, $location, $nEmployees, $dateCreate, $teamRemote, {$employeeString}"
    }



}
