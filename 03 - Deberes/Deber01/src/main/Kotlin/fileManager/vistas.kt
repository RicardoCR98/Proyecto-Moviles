package fileManager
import model.dao.DepartamentoDAO
import model.dao.EmpleadoDAO
import model.dao.imp.DepartamentoDAOImpl
import model.dao.imp.EmpleadoDAOImpl
import model.entity.Departamento
import model.entity.Empleado
import java.time.LocalDate
import java.util.*
import kotlin.system.exitProcess

@Suppress("UNREACHABLE_CODE")
class vistas {

    private val departamentoDAO: DepartamentoDAO = DepartamentoDAOImpl(manager("src/main/kotlin/data/departamentos.txt"))
    private val empleadoDAO: EmpleadoDAO = EmpleadoDAOImpl(manager("src/main/kotlin/data/empleados.txt"))
    private val scanner: Scanner = Scanner(System.`in`)

    fun start() {
        var exit = false
        while (!exit) {
            println("======= Bienvenido a la Gestión de Empleados y Departamentos =======")
            println("1. Opciones Departamento")
            println("2. Opciones Empleado")
            println("3. Salir")
            println("==========================================")

            when (readMenuOption()) {
                1 -> startDepartamento()
                2 -> startEmpleado()
                3 -> exit = true
                else -> println("Opción inválida. Ingrese nuevamente")
            }
            println()
        }
    }

    private fun startDepartamento() {
        var exit = false
        while (!exit) {
            println("======= Menú Departamento =======")
            println("1. Crear Departamento")
            println("2. Mostrar todos los Departamentos")
            println("3. Actualizar Departamento")
            println("4. Eliminar Departamento")
            println("5. Salir")
            println("================================")

            when (readMenuOption()) {
                1 -> createDepartamento()
                2 -> showAllDepartamentos()
                3 -> updateDepartamento()
                4 -> deleteDepartamento()
                5 -> exit = true
                else -> println("Opción inválida. Ingrese nuevamente")
            }
            println()
        }
    }

    private fun startEmpleado() {
        var exit = false
        while (!exit) {
            println("======= Menú Empleado =======")
            println("1. Crear Empleado")
            println("2. Mostrar todos los Empleados")
            println("3. Actualizar Empleado")
            println("4. Eliminar Empleado")
            println("5. Salir")
            println("================================")

            when (readMenuOption()) {
                1 -> createEmpleado()
                2 -> showAllEmpleados()
                3 -> updateEmpleado()
                4 -> deleteEmpleado()
                5 -> exit = true
                else -> println("Opción inválida. Ingrese nuevamente")
            }
            println()
        }
    }

    private fun readMenuOption(): Int {
        print("Ingresa una opción: ")
        val input = scanner.nextLine()

        println("El valor ingresado es:$input")
        return try {
            input.toInt()
        } catch (e: NumberFormatException) {
            println("Opcion invalida, cerrando el sistema...")
            exitProcess(0)
            -1
        }
    }

    //PRIMERA PARTE EMPLEADOS
    private fun createEmpleado() {
        println("==== Crear Empleado ====")
        print("Ingresa el nombre del Empleado: ")
        val nombre = scanner.nextLine()

        print("Ingresa el cargo que va a desempeñar el Empleado: ")
        val cargo = scanner.nextLine()

        print("Ingresa el salario del Empleado: ")
        val salario = scanner.nextDouble()

        scanner.nextLine() // Limpiar el buffer del scanner

        // Fecha establecida automáticamente por el sistema
        val fechaContratacion = LocalDate.now()

        print("Ingresa si el Empleado está activo (true/false): ")
        val isActive = scanner.nextBoolean()

        // Crear un objeto Empleado con todas las propiedades
        val empleado = Empleado(empleadoDAO.getAll().size+1,nombre, cargo, salario, fechaContratacion, isActive)
        empleadoDAO.create(empleado)
        println("\nEmpleado creado exitosamente")
        scanner.nextLine() //Limpiamos el buffer
    }

    private fun showAllEmpleados() {
        println("==== Mostrar todos los Empleados ====")
        val empleados = empleadoDAO.getAll()
        if (empleados.isEmpty()) {
            println("No hay Empleados disponibles")
        } else {
            for (empleado in empleados) {
                println(empleado)
            }
        }
    }

    private fun updateEmpleado() {
        showAllEmpleados()

        println("==== Actualizar Empleado ====")
        print("Ingresa el ID del empleado a actualizar: ")
        val id = scanner.nextInt()
        scanner.nextLine() // Consumir la línea en blanco restante

        // Obtener el empleado actual
        val empleadoActual = empleadoDAO.getById(id)
        if (empleadoActual == null) {
            println("No se encontró un empleado con el ID $id.")
            return
        }

        // Mostrar las propiedades actuales del empleado
        println("Empleado actual: $empleadoActual")

        // Permitir al usuario actualizar propiedades individuales
        print("Ingresa el nuevo nombre del Empleado (o deja en blanco para mantener el actual): ")
        val nuevoNombre = scanner.nextLine().takeIf { it.isNotBlank() } ?: empleadoActual.getName()

        print("Ingresa el nuevo cargo del Empleado (o deja en blanco para mantener el actual): ")
        val nuevoCargo = scanner.nextLine().takeIf { it.isNotBlank() } ?: empleadoActual.getPosition()

        print("Ingresa el nuevo salario del Empleado (o deja en blanco para mantener el actual): ")
        val nuevoSalario = scanner.nextDouble().takeIf { it > 0 } ?: empleadoActual.getSalary()

        scanner.nextLine() // Limpiar el buffer del scanner

        print("El Empleado está activo (true/false, deja en blanco para mantener el actual): ")
        val nuevoIsActive = scanner.nextLine().takeIf { it.isNotBlank() }?.toBoolean() ?: empleadoActual.getActive()

        // Crear un objeto Empleado con las propiedades actualizadas
        val empleadoActualizado = Empleado(id, nuevoNombre, nuevoCargo, nuevoSalario, empleadoActual.getDateHire(), nuevoIsActive)

        // Llamar al método update de EmpleadoDAO para aplicar los cambios
        empleadoDAO.update(empleadoActualizado)
        println("\n\nEmpleado actualizado exitosamente")
    }


    private fun deleteEmpleado() {
        println("==== Eliminar Empleado ====")
        showAllEmpleados()
        print("Ingresa el ID del Empleado a eliminar: ")
        val id = scanner.nextInt()
        empleadoDAO.delete(id)
        println("\nEmpleado eliminado exitosamente")
        scanner.nextLine() //Limpiamos el buffer
    }

    //SEGUNDA PARTE DEPTOS
    private fun createDepartamento() {
        println("==== Crear Departamento ====")
        print("Ingresa el nombre del Departamento: ")
        val nombre = scanner.nextLine()

        print("Ingresa la ubicación del Departamento: ")
        val ubicacion = scanner.nextLine()

        print("Ingresa el número de empleados en el Departamento: ")
        val nEmpleados = scanner.nextInt()
        scanner.nextLine() // Limpiar el buffer del scanner

        print("El equipo remoto es remoto? (true/false): ")
        val equipoRemoto = scanner.nextBoolean()
        scanner.nextLine() // Limpiar el buffer del scanner

        // Fecha establecida automáticamente por el sistema
        val fechaCreacion = LocalDate.now()

        // Crear un objeto Departamento con todas las propiedades
        val departamento = Departamento(
            departamentoDAO.getAll().size + 1,
            nombre,
            ubicacion,
            nEmpleados,
            fechaCreacion,
            equipoRemoto,
            mutableListOf()
        )
        departamentoDAO.create(departamento)

        // Comentado para evitar consumir una línea en blanco en el buffer del scanner
        // showAllEmpleados()

        // Agregar empleados al departamento
        for (i in 1..nEmpleados) {
            print("Ingresa el id del empleado $i a agregar al Departamento: ")
            val empleadoId = scanner.nextInt()
            scanner.nextLine() // Limpiar el buffer del scanner

            // Buscar el empleado por su id y agregarlo al departamento
            val empleado = empleadoDAO.getById(empleadoId)
            if (empleado != null) {
                departamento.addEmpleado(empleado)
                println("Empleado ${empleado.getName()} con ID $empleadoId agregado al Departamento.")
            } else {
                println("No se encontró un empleado con el id $empleadoId.")
            }
        }
        // Guardar los cambios en el departamento después de agregar empleados
        departamentoDAO.update(departamento)
        println("\nNuevo Departamento Añadido con Empleados")
    }


    private fun showAllDepartamentos() {
        println("==== Listando departamentos ====")
        val departamentos = departamentoDAO.getAll()

        if (departamentos.isEmpty()) {
            println("No hay Departamentos disponibles")
        } else {
            for (departamento in departamentos) {
                println(departamento)
            }
        }
    }

    private fun updateDepartamento() {
        showAllDepartamentos()

        println("==== Actualizar Departamento ====")
        print("Ingresa el ID del departamento a actualizar: ")
        val id = scanner.nextInt()
        scanner.nextLine() // Consumir la línea en blanco restante

        // Obtener el departamento actual
        val departamentoActual = departamentoDAO.getById(id)
        if (departamentoActual == null) {
            println("No se encontró un departamento con el ID $id.")
            return
        }

        // Mostrar las propiedades actuales del departamento
        println("Departamento actual: $departamentoActual")

        // Obtener el número de empleados actual
        val nEmpleadosActual = departamentoActual.getNEmployees()

        // Permitir al usuario actualizar propiedades individuales
        print("Ingresa el nuevo nombre del Departamento (o deja en blanco para mantener el actual): ")
        val nuevoNombre = scanner.nextLine().takeIf { it.isNotBlank() } ?: departamentoActual.getName()

        print("Ingresa la nueva ubicación del Departamento (o deja en blanco para mantener la actual): ")
        val nuevaUbicacion = scanner.nextLine().takeIf { it.isNotBlank() } ?: departamentoActual.getLocation()

        print("Ingresa el nuevo número de empleados en el Departamento (o deja en blanco para mantener el actual): ")
        val nuevoNEmpleadosInput = scanner.nextLine().trim()
        val nuevoNEmpleados = if (nuevoNEmpleadosInput.isNotBlank()) {
            nuevoNEmpleadosInput.toIntOrNull()?.takeIf { it > 0 } ?: nEmpleadosActual
        } else {
            nEmpleadosActual
        }

        // Ajustar la lista de empleados si el número de empleados ha cambiado
        val nuevosEmpleados = if (nEmpleadosActual != nuevoNEmpleados) {
            departamentoActual.getListEmployees().take(nuevoNEmpleados).toMutableList()
        } else {
            departamentoActual.getListEmployees()
        }.toMutableList()

        print("El equipo que se está actualizando es remoto? (true/false, deja en blanco para mantener el actual): ")
        val nuevoEquipoRemoto = scanner.nextLine().takeIf { it.isNotBlank() }?.toBoolean() ?: departamentoActual.getTeamRemote()

        // Crear un objeto Departamento con las propiedades actualizadas
        val departamentoActualizado = Departamento(id, nuevoNombre, nuevaUbicacion, nuevoNEmpleados, LocalDate.now(), nuevoEquipoRemoto, nuevosEmpleados)

        // Llamar al método update de DepartamentoDAO para aplicar los cambios
        departamentoDAO.update(departamentoActualizado)
        println("\n\nDepartamento actualizado exitosamente")
    }


    private fun deleteDepartamento() {
        showAllDepartamentos()

        println("==== Eliminar Departamento ====")
        print("Ingresa el ID del departamento a eliminar: ")
        val id = scanner.nextInt()

        departamentoDAO.delete(id)
        println("\nDepartamento eliminado exitosamente")
        scanner.nextLine() //Limpiamos el buffer
    }


}
