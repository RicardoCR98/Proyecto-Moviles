package com.example.examenib.almacenamiento

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.examenib.modelo.entity.Departamento
import com.example.examenib.modelo.entity.Empleado
import java.time.LocalDateTime

class BaseDatosMemoria {
    @RequiresApi(Build.VERSION_CODES.O)
    companion object {
        val listaEmpleado = mutableListOf<Empleado>()
        val listaDepartamento = mutableListOf<Departamento>()

        init {
            listaEmpleado.add(
                Empleado(
                    1,
                    "Juan",
                    "Product manager",
                    2000.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    2,
                    "Pepe",
                    "Produc owner",
                    2000.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    3,
                    "Rocio",
                    "Ui",
                    1500.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    4,
                    "Lucho",
                    "DevOps",
                    1700.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    5,
                    "David",
                    "Developer",
                    1500.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    6,
                    "Maria",
                    "Product owner",
                    2200.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    7,
                    "Carlos",
                    "UX Designer",
                    1800.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    8,
                    "Laura",
                    "DevOps Engineer",
                    1900.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    9,
                    "Pedro",
                    "Frontend Developer",
                    1600.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    10,
                    "Sofía",
                    "Backend Developer",
                    1700.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    11,
                    "Miguel",
                    "Scrum Master",
                    2100.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    12,
                    "Elena",
                    "Mobile Developer",
                    1750.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    13,
                    "Javier",
                    "Data Scientist",
                    2000.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    14,
                    "Isabel",
                    "QA Engineer",
                    1800.00,
                    LocalDateTime.now(),
                    true
                )
            )

            listaEmpleado.add(
                Empleado(
                    15,
                    "Alejandro",
                    "Full Stack Developer",
                    1900.00,
                    LocalDateTime.now(),
                    true
                )
            )

    // Creación de departamentos con sus respectivos empleados
            listaDepartamento.add(
                Departamento(1,
                    "Desarrollo",
                    "Carcelen",
                    2,
                    LocalDateTime.now(),
                    false,
                    mutableListOf(
                        listaEmpleado[0],
                        listaEmpleado[1]
                    )
                )
            )

            listaDepartamento.add(
                Departamento(2,
                    "Ventas",
                    "Villaflora",
                    2,
                    LocalDateTime.now(),
                    true,
                    mutableListOf(
                        listaEmpleado[2],
                        listaEmpleado[3]
                    )
                )
            )

            listaDepartamento.add(
                Departamento(3,
                    "Soporte Técnico",
                    "Centro",
                    5,
                    LocalDateTime.now(),
                    false,
                    mutableListOf(
                        listaEmpleado[4],
                        listaEmpleado[5],
                        listaEmpleado[6],
                        listaEmpleado[7],
                        listaEmpleado[8]
                    )
                )
            )

            listaDepartamento.add(
                Departamento(4,
                    "Recursos Humanos",
                    "La Mariscal",
                    3,
                    LocalDateTime.now(),
                    true,
                    mutableListOf(
                        listaEmpleado[9],
                        listaEmpleado[10],
                        listaEmpleado[11]
                    )
                )
            )

            listaDepartamento.add(
                Departamento(5,
                    "Finanzas",
                    "Quito Norte",
                    3,
                    LocalDateTime.now(),
                    true,
                    mutableListOf(
                        listaEmpleado[12],
                        listaEmpleado[13],
                        listaEmpleado[14]
                    )
                )
            )

        }

    }
}