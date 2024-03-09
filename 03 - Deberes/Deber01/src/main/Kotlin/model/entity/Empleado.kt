package model.entity

import java.time.LocalDate

class Empleado(
    private val idEmployee: Int,
    private val name: String,
    private val position: String,
    private var salary: Double,
    private val dateHire: LocalDate,
    private var isActive: Boolean
) {
    //El constructor se genera de forma implicita, pero se puede añadir un constructor secundario si es necesario
    //Los getters y setters se  generan automáticamente de forma implícita
    //Unicamente se pueden acceder a ellos si el atributo es publico, caso contrario, debemos de crear el getter manualmente

    // Getter para idEmployee
    fun getIdEmployee(): Int {
        return idEmployee
    }
    fun getName():String{
        return name
    }
    fun getPosition():String{
        return position
    }
    fun getSalary():Double{
        return salary
    }
    fun getDateHire():LocalDate{
        return dateHire
    }
    fun getActive():Boolean{
        return isActive
    }

    override fun toString(): String {
        return "[$idEmployee, $name, $position, $salary, $dateHire, $isActive]"
    }



}
