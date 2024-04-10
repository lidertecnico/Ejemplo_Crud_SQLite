package aplicacionesmoviles.avanzado.todosalau.ejemplocrudsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.ejemplocrudsqlite.database.DatabaseHelper;
import aplicacionesmoviles.avanzado.todosalau.ejemplocrudsqlite.model.Usuario;

public class UsuarioDAO {
    private SQLiteDatabase db; // Objeto para interactuar con la base de datos
    private DatabaseHelper dbHelper; // Instancia de DatabaseHelper para crear y actualizar la base de datos

    // Constructor que recibe el contexto de la aplicación y crea una instancia de DatabaseHelper
    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Método para abrir la conexión con la base de datos en modo escritura
    public void abrir() {
        db = dbHelper.getWritableDatabase();
    }

    // Método para cerrar la conexión con la base de datos
    public void cerrar() {
        dbHelper.close();
    }

    // Método para insertar un nuevo usuario en la tabla 'usuarios'
    public long insertarUsuario(String nombre, String email) {
        ContentValues values = new ContentValues(); // Objeto para almacenar los valores a insertar
        values.put("nombre", nombre); // Inserción del nombre del usuario
        values.put("email", email); // Inserción del email del usuario
        return db.insert("usuarios", null, values); // Ejecución de la inserción y retorno del ID del nuevo registro
    }

    // Método para obtener todos los usuarios de la tabla 'usuarios'
    public List<Usuario> obtenerTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>(); // Lista para almacenar los usuarios obtenidos
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios", null); // Ejecución de la consulta SQL

        // Iteración sobre los resultados del cursor para obtener los datos de cada usuario
        if (cursor.moveToFirst()) {
            do {
                Usuario usuario = new Usuario(); // Creación de una nueva instancia de Usuario
                usuario.setId(cursor.getInt(0)); // Asignación del ID del usuario
                usuario.setNombre(cursor.getString(1)); // Asignación del nombre del usuario
                usuario.setEmail(cursor.getString(2)); // Asignación del email del usuario
                usuarios.add(usuario); // Agregación del usuario a la lista
            } while (cursor.moveToNext());
        }
        cursor.close(); // Cierre del cursor
        return usuarios; // Retorno de la lista de usuarios
    }

    // Método para actualizar los datos de un usuario en la tabla 'usuarios'
    public void actualizarUsuario(int id, String nombre, String email) {
        ContentValues values = new ContentValues(); // Objeto para almacenar los nuevos valores
        values.put("nombre", nombre); // Asignación del nuevo nombre
        values.put("email", email); // Asignación del nuevo email
        // Actualización del usuario con el ID proporcionado
        db.update("usuarios", values, "id = ?", new String[]{String.valueOf(id)});
    }

    // Método para eliminar un usuario de la tabla 'usuarios' mediante su ID
    public void eliminarUsuario(int id) {
        // Eliminación del usuario con el ID proporcionado
        db.delete("usuarios", "id = ?", new String[]{String.valueOf(id)});
    }
}
