package aplicacionesmoviles.avanzado.todosalau.ejemplocrudsqlite.model;

public class Usuario {
    private int id; // Identificador único del usuario
    private String nombre; // Nombre del usuario
    private String email; // Correo electrónico del usuario

    // Constructor que inicializa un Usuario con un nombre y un correo electrónico
    public Usuario(String nombre, String email) {
        this.nombre = nombre; // Asigna el nombre proporcionado
        this.email = email; // Asigna el correo electrónico proporcionado
    }

    // Constructor vacío (por defecto)
    public Usuario(){
        // Se deja vacío
    }

    // Métodos para acceder y modificar el atributo id
    public int getId() {
        return id; // Devuelve el id del usuario
    }

    public void setId(int id){
        this.id = id; // Establece el id del usuario
    }

    // Métodos para acceder y modificar el atributo nombre
    public String getNombre() {
        return nombre; // Devuelve el nombre del usuario
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; // Establece el nombre del usuario
    }

    // Métodos para acceder y modificar el atributo email
    public String getEmail() {
        return email; // Devuelve el correo electrónico del usuario
    }

    public void setEmail(String email) {
        this.email = email; // Establece el correo electrónico del usuario
    }
}
