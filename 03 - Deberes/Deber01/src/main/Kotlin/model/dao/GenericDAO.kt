package model.dao
//Las clases y sus miembros son públicos por defecto
interface GenericDAO<T, ID> {
    //Se crea esta clase puesto que siempre se va aplicar los mismos metodos para las demas clases
    //Tambien para futuro escalamiento en BD
    fun getById(id: ID): T?
    fun getAll(): MutableList<T>
    fun create(entity: T)
    fun update(entity: T)
    fun delete(id: ID)
    fun save(entity: List<T>)

}