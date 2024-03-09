package model.dao

import model.entity.Empleado

//En Kotlin, no hay interfaces implements o extends como en Java.
//En su lugar, simplemente se utiliza : para indicar la herencia de una interfaz o de una clase.
interface EmpleadoDAO: GenericDAO<Empleado, Int> {

}