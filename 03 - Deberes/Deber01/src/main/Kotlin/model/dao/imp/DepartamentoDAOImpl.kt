package model.dao.imp

import fileManager.manager
import model.dao.DepartamentoDAO
import model.entity.Departamento
import model.entity.Empleado
import java.time.LocalDate

class DepartamentoDAOImpl(private val fileHander:manager): DepartamentoDAO {

    //Usamos var porque se va añadiendo a la lista
    private var departamentos: MutableList<Departamento>? = null
    val date = LocalDate.now()

    init {
        // Load data from the file when the class is initialized
        this.departamentos = loadData()
    }
    private fun loadData(): MutableList<Departamento> {
        try {
            val dataLines = fileHander.readData()
            val parsedDepartamentos = dataLines.map { line ->
                val tokens = line.split(", ")

                val idDepto = tokens[0].trim().toInt()
                val name = tokens[1].trim()
                val location = tokens[2].trim()
                val nEmployees = tokens[3].trim().toInt()
                val dateCreate = LocalDate.parse(tokens[4].trim()) // Assuming date is formatted as text
                val teamRemote = tokens[5].trim().toBoolean()

                val employees = if (tokens.size > 6) {
                    val employeeData = tokens.subList(6, tokens.size).joinToString(", ")
                    val employeeList = parseEmployeeList(employeeData)
                    employeeList.toMutableList()
                } else {
                    mutableListOf()
                }
                Departamento(idDepto, name, location, nEmployees, dateCreate, teamRemote, employees)
            }.toMutableList()

            return parsedDepartamentos
        } catch (e: Exception) {
            println("Error loading data: ${e.message}")
            // Handle the error appropriately, such as logging or throwing a custom exception
            return mutableListOf()
        }
    }

    private fun parseEmployeeList(employeeData: String): List<Empleado> {
        val employeeList = mutableListOf<Empleado>()

        // Extraer la cadena de empleados dentro de las llaves
        val employeesString = employeeData.substring(1, employeeData.length - 1)

        // Dividir la cadena de empleados en una lista de cadenas de empleado
        val employeeTokens = employeesString.split("], [")

        for (employeeToken in employeeTokens) {
            // Eliminar corchetes restantes y dividir los datos del empleado
            val employeeValues = employeeToken.replace("[", "").replace("]", "").split(", ")

            val id = employeeValues[0].toInt()
            val name = employeeValues[1]
            val position = employeeValues[2]
            val salary = employeeValues[3].toDouble()
            val date = LocalDate.parse(employeeValues[4])
            val isActive = employeeValues[5].toBoolean()

            val employee = Empleado(id, name, position, salary, date, isActive)
            employeeList.add(employee)
        }

        return employeeList
    }




    override fun getById(id: Int): Departamento? {
        if (this.departamentos != null) {
            for (departamentos in this.departamentos!!) {
                if (departamentos.getidDepto() == id) {
                    return departamentos
                }
            }
        }
        return null
    }

    override fun getAll(): MutableList<Departamento> {
        if(this.departamentos.isNullOrEmpty()){
            this.departamentos = ArrayList()
            // Agregar elementos a la lista
            departamentos?.add(
                Departamento(1, "Desarrollo","Carcelen",2,date, false,
                mutableListOf(
                    Empleado(1, "Juan", "Product manager", 2000.00, date, true),
                    Empleado(2, "Pepe", "Produc owner", 2000.00,date, true)
                ))
            )
            departamentos?.add(
                Departamento(2,"Ventas","Villaflora",2,date,true,
                mutableListOf(
                    Empleado(3, "Rocio", "Ui", 1500.00,date, true),
                    Empleado(4, "Lucho", "DevOps", 1700.00,date, true)
                ))
            )
            departamentos?.add(
                Departamento(3, "Soporte Técnico", "Centro", 5, date, false,
                mutableListOf(
                    Empleado(5, "David", "Developer", 1500.00,date, true),
                    Empleado(6, "Maria", "Product owner", 2200.00, date, true),
                    Empleado(7, "Carlos", "UX Designer", 1800.00, date, true),
                    Empleado(8, "Laura", "DevOps Engineer", 1900.00, date, true),
                    Empleado(9, "Pedro", "Frontend Developer", 1600.00, date, true)
                ))
            )
            departamentos?.add(
                Departamento(4, "Recursos Humanos", "La Mariscal", 3, date, true,
                mutableListOf(
                    Empleado(10, "Sofía", "Backend Developer", 1700.00, date, true),
                    Empleado(11, "Miguel", "Scrum Master", 2100.00, date, true),
                    Empleado(12, "Elena", "Mobile Developer", 1750.00, date, true)
                ))
            )
            departamentos?.add(
                Departamento(5, "Finanzas", "Quito Norte", 3, date, true,
                mutableListOf(
                    Empleado(13, "Javier", "Data Scientist", 2000.00, date, true),
                    Empleado(14, "Isabel", "QA Engineer", 1800.00, date, true),
                    Empleado(15, "Alejandro", "Full Stack Developer", 1900.00, date, true)
                ))
            )
        }
        save(this.departamentos!!)
        return this.departamentos!!
    }

    override fun create(d: Departamento) {
        getAll()
        this.departamentos?.add(d)
        save(departamentos!!)
    }

    override fun update(d: Departamento) {
        for (i in 0 until this.departamentos!!.size) {
            if (this.departamentos!!.get(i).getidDepto() == d.getidDepto()) {
                this.departamentos!!.set(i, d)
                save(this.departamentos!!)
            }
        }
    }


    override fun delete(id: Int) {
        for(i in 0 until this.departamentos!!.size){
            if(this.departamentos!!.get(i).getidDepto() == id){
                this.departamentos!!.removeAt(i)
                save(departamentos!!)
            }
        }
    }

    override fun save(d: List<Departamento>) {
        val deptoData = d.map { depto ->
            val listEmployees = depto.getListEmployees().joinToString(", ") { it.toString() }
            "${depto.getidDepto()}, ${depto.getName()}, ${depto.getLocation()}, ${depto.getNEmployees()}, ${depto.getDateCreate()}, ${depto.getTeamRemote()}, {$listEmployees}"
        }
        fileHander.writeData(deptoData)
    }



}