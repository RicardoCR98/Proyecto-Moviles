package fileManager

import java.io.File
import java.io.FileWriter
import java.io.IOException

class manager(private val filename:String) {

    private val file: File = File(filename);
    init {
        createFileIfNotExists()
    }

    private fun createFileIfNotExists() {
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        }catch (e: IOException){
            println("Error al cargar el archivo: ${e.message}")
        }
    }

    fun readData():List<String>{
        return file.readLines();
    }

    fun writeData(data:List<String>){
        try {
            val fileWrite = FileWriter(file)
            for(line in data){
                fileWrite.write(line)
                fileWrite.write(System.lineSeparator())
            }
            fileWrite.close()
        }catch (e: IOException){
            println("Error al escrbir en el archivo: ${e.message}")
        }
    }

}