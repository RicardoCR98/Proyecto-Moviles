package model.dao.imp

import fileManager.manager
import model.dao.EmpleadoDAO
import model.entity.Empleado
import java.time.LocalDate

class EmpleadoDAOImpl(private val fileHander: manager): EmpleadoDAO {

    //Usamos var porque se va añadiendo a la lista
    private var empleados: MutableList<Empleado>? = null
    val date = LocalDate.now()

    init {
        // Load data from the file when the class is initialized
        this.empleados = loadData()
    }

    private fun loadData(): MutableList<Empleado> {
        try {
            val dataLines = fileHander.readData()
            return dataLines.map { line ->
                val tokens = line.split(", ")

                val id = tokens[0].toInt()
                val name = tokens[1]
                val position = tokens[2]
                val salary = tokens[3].toDouble()
                val date = LocalDate.parse(tokens[4]) // Assuming date is formatted as text
                val isActive = tokens[5].toBoolean()

                Empleado(id, name, position, salary, date, isActive)
            }.toMutableList()
        } catch (e: Exception) {
            println("Error loading data: ${e.message}")
            // Handle the error appropriately, such as logging or throwing a custom exception
            return mutableListOf()
        }
    }


    override fun getById(id: Int): Empleado? {
        val empleados = this.empleados
        if (empleados != null) {
            for (empleado in empleados) {
                if (empleado.getIdEmployee() == id) {
                    return empleado
                }
            }
        }
        return null

        //Esto se puede reemplazar con return empleados?.find { it.getId() == id }
    }

    override fun getAll(): MutableList<Empleado> {
        if(empleados.isNullOrEmpty()){
            empleados = ArrayList()
            // Agregar elementos a la lista
            empleados?.add(Empleado(1, "Juan", "Product manager", 2000.00, date, true))
            empleados?.add(Empleado(2, "Pepe", "Produc owner", 2000.00,date, true))
            empleados?.add(Empleado(3, "Rocio", "Ui", 1500.00,date, true))
            empleados?.add(Empleado(4, "Lucho", "DevOps", 1700.00,date, true))
            empleados?.add(Empleado(5, "David", "Developer", 1500.00,date, true))
            empleados?.add(Empleado(6, "Maria", "Product owner", 2200.00, date, true))
            empleados?.add(Empleado(7, "Carlos", "UX Designer", 1800.00, date, true))
            empleados?.add(Empleado(8, "Laura", "DevOps Engineer", 1900.00, date, true))
            empleados?.add(Empleado(9, "Pedro", "Frontend Developer", 1600.00, date, true))
            empleados?.add(Empleado(10, "Sofía", "Backend Developer", 1700.00, date, true))
            empleados?.add(Empleado(11, "Miguel", "Scrum Master", 2100.00, date, true))
            empleados?.add(Empleado(12, "Elena", "Mobile Developer", 1750.00, date, true))
            empleados?.add(Empleado(13, "Javier", "Data Scientist", 2000.00, date, true))
            empleados?.add(Empleado(14, "Isabel", "QA Engineer", 1800.00, date, true))
            empleados?.add(Empleado(15, "Alejandro", "Full Stack Developer", 1900.00, date, true))

        }
        save(empleados!!)
        return empleados as MutableList<Empleado>
    }

    override fun create(e: Empleado) {
        val lista = getAll()
        lista.add(e)
        save(lista)
    }

    //Se usa el operador "?" dado que es nulleable
    override fun update(e: Empleado) {
        val emp = getAll()
        for (i in 0 until emp.size) {
            if (emp.get(i).getIdEmployee() == e.getIdEmployee()) {
                emp.set(i, e)
                save(emp)
            }
        }
    }

    override fun delete(id: Int) {
        val emp = getAll()
        for(i in 0 until emp.size){
            if(emp.get(i).getIdEmployee() == id){
                emp.removeAt(i)
                save(emp)
            }
        }

    }

    override fun save(e: List<Empleado>) {
        val empleadoData = e.map {emp ->
            "${emp.getIdEmployee()}, ${emp.getName()}, ${emp.getPosition()}, ${emp.getSalary()}, ${emp.getDateHire()}, ${emp.getActive()}"
        }
        fileHander.writeData(empleadoData)
    }

}