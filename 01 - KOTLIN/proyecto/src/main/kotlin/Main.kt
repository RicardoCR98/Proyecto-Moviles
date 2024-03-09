import sun.font.TrueTypeFont
import java.util.*
fun main() {
    println("Hello World!")
    // INMUTABLES (NO se reasignan "=")
    val inmutable:String ="Adrian";
    // Inmutable ="Vicente";

    // Mutable (Re asignar)
    var mutable:String ="Vicente";
    mutable ="Adrian";

    // val > var
    // Duck Typing
    var ejemploVariable ="Gary Campaña"
    val edadEjejmplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable =edadEjemplo

    //variable primitiva
    val nombreProfesor:String ="Adrian Eguez"
    val sueldo:Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    //Clases Java
    val fechaNacimiento: Date = Date()

    // SWITCH
    val estadoCivilWhen ="C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" ->{
            println("Soltero")
        }
        else ->{
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen =="S")
    val coqueto = if (esSoltero) "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00,20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) //Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00,tasa=14.00)

    val sumaUno = Suma(1, 1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    // Arreglo Estatico
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico)

    // Arreglo Dinámicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // FOR EACH -> Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach {
        valorActual: Int -> println("Valor actual: ${valorActual}")
        }
    // it (en ingles eso) significa el elemento iterado
    arregloDinamico.forEach { println("Valor actual: ${it}") }

    arregloEstatico
        .forEachIndexed { indice: Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    // MAP -> Muta el arreglo (Cambia el arreglo)
    // 1) Enviemos el nuevo valor de la iteracion
    // 2) Nos devuelve es un NUEVO ARREGLO
    // con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }

    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }

    // Filter -> FILTRAR EL ARREGLO
    // 1) Devolver una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            // Expresion Condicion
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter {
        it <= 5
    }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //OR AND
    //OR -> ANY (alguno cumple?)
    //AND -> ALL (todos cumplen?)

    val respuestaAny: Boolean = arregloDinamico.any{
        valorActual:Int -> return@any(valorActual>5)
    }
    println(respuestaAny) //True

    val respuestaAll:Boolean = arregloDinamico.all{
        valorActual:Int -> return@all(valorActual>5)
    }
    println(respuestaAll) //false



    //REDUCE
    //Valor acumulado = 0 (siempre 0 en lenguaje Kotlin)
    //[1,2,3,4,5] -> Sumeme todos los valores del arreglo
    //valorIteraccion1 = valorEmpieza + 1 = 0 +1 = 1-> Iteracción1
    //valorIteracción2 = valorIteracción2 + 2 = 1 + 2 = 3 -> Iteracción2
    //valorIteracción3 = valorIteracción3 + 3 = 3 + 3 = 6 -> Iteracción3
    //valorIteracción4 = valorIteracción4 + 4 = 6 + 4 = 10 -> Iteraccion4
    //valorIteracción5 = valorIteracción5 + 5 = 10 + 5 = 15 -> Itereaccion5

    val respuestaReduce: Int = arregloDinamico.reduce{ //Acumulado = 0 -> Siempre empieza en 0
        acumulado: Int, valorActual: Int -> return@reduce (acumulado + valorActual) //Logica Negocio
    }
    println(respuestaReduce) //78
    //acumulado + (itemCarrito.cantidad + itemCarrito.precio)
}


abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno:Int,
        dos: Int
    ){ //Bloque de código del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros(
    //Ejemplo:
    // uno: Int, (Parametro (Sin modificador de acceso))
    // private var uno: Int, //Propiedad publica Clase numero uno
    // var uno: Int //Propiedad de la clase (por defecto es PUBLIC)
    //public var uno: Int,
    protected val numeroUno: Int, //Propiedad de la clase protected numeros.numeroUno
    protected val numeroDos: Int, //Propiedad de la clase protected numeros.numeroDos
){
    //var cedula:string ="" (Public es por defecto)
    //private valorCalculado: Int = 0 (private)

    init{ //Bloque constructor primario
        this.numeroUno; this.numeroDos //this es opcional
        numeroUno; numeroDos //Sin el this, es lo mismo
        println("Inicializando")
    }
}


class Suma( //Constructor Primario Suma
    unoParametro:Int, //Parametro
    dosParametro: Int, //Parametro
): Numeros(unoParametro, dosParametro){ //Extendido y mandando los parametros (suoer)
    init{ //Bloque codigo constructor primario
        this.numeroUno
        this.numeroDos
    }

    constructor( //Segundo Constructor
        uno: Int?, //Parametros
        dos:Int//Parametros
    ):this(
        if(uno==null)0 else uno, dos
    )

    constructor( //Tercer Constructor
        uno: Int, //Parametros
        dos:Int? //Parametros
    ):this(
        uno,
        if(dos==null)0 else dos
    )

    constructor(//  cuarto constructor
        uno: Int?, // parametros
        dos: Int? // parametros
    ) : this(  // llamada constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else uno
    )


    // public por defecto, o usar private o protected
    public fun sumar(): Int {
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total)
        agregarHistorial(total)
        return total
    }
    // Atributos y Metodos "Compartidos"
    companion object {
        // entre las instancias
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
        }
    }
}

//Void -> Unit
fun imprimirNombre(nombre:String):Unit{
    //"Nombre: " + nombre
    println ("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa:Double=12.00,// Opcional (defecto)
    bonoEspecial:Double?=null,//Nullable
    ):Double{
    // Int -> Int? (nulleable)
    // String -> String? (nulleable)
    // Date -> Date? (nulleable)
    if(bonoEspecial ==null){
        return sueldo*(100/tasa)
    }else{
        return sueldo * (100/tasa)+ bonoEspecial
    }
}
