package aplicacionesmoviles.avanzado.todosalau.ejemplocrudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.ejemplocrudsqlite.adapter.UsuarioListAdapter;
import aplicacionesmoviles.avanzado.todosalau.ejemplocrudsqlite.dao.UsuarioDAO;
import aplicacionesmoviles.avanzado.todosalau.ejemplocrudsqlite.model.Usuario;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables
    private UsuarioDAO usuarioDAO;

    private EditText etlNombre, etlEmail;
    private Button btnAgregar, btnMostrar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de la base de datos y conexión
        usuarioDAO = new UsuarioDAO(this);
        usuarioDAO.abrir();

        // Obtención de referencias a elementos de la interfaz de usuario
        etlNombre = findViewById(R.id.etlNombre);
        etlEmail = findViewById(R.id.etlEmail);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnMostrar = findViewById(R.id.btnMostrar);
        listView = findViewById(R.id.listView);

        // Configuración de listener para el botón de agregar
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarUsuario();
            }
        });

        // Configuración de listener para el botón de mostrar
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarUsuarios();
                btnMostrar.setVisibility(View.GONE);
            }
        });

    }

    // Método para agregar un usuario
    private void agregarUsuario() {
        String nombre = etlNombre.getText().toString().trim();
        String email = etlEmail.getText().toString().trim();

        // Validación de campos
        if (!nombre.isEmpty() && !email.isEmpty()) {
            // Inserción del usuario en la base de datos
            long resultado = usuarioDAO.insertarUsuario(nombre, email);
            if (resultado != -1) {
                Toast.makeText(MainActivity.this, "Usuario agregado correctamente", Toast.LENGTH_SHORT).show();
                etlNombre.setText("");
                etlEmail.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Error al agregar usuario", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
        // Actualización de la lista de usuarios después de agregar uno nuevo
        actualizarListaUsuarios();
    }

    // Método para mostrar usuarios
    private void mostrarUsuarios() {
        // Obtención de la lista de usuarios desde la base de datos
        List<Usuario> usuarios = usuarioDAO.obtenerTodosUsuarios();
        // Construcción de una cadena con los detalles de los usuarios
        StringBuilder stringBuilder = new StringBuilder();
        for (Usuario usuario : usuarios) {
            stringBuilder.append("ID: ").append(usuario.getId()).append(", Nombre: ").append(usuario.getNombre().toString()).append(", Email: ").append(usuario.getEmail().toString()).append("\n");
        }
        // Creación de un adaptador personalizado y configuración del ListView
        UsuarioListAdapter adapter = new UsuarioListAdapter(this, usuarios);
        listView.setAdapter(adapter);

        // Configuración de listeners para el adaptador
        adapter.setOnEditClickListener(new UsuarioListAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int position) {
                // Lógica para editar un usuario
                Usuario usuario = usuarios.get(position);
                etlNombre.setText(usuario.getNombre());
                etlEmail.setText(usuario.getEmail());
                int idUsuario = usuario.getId();
                btnAgregar.setText("Guardar");
                btnAgregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actualizarUsuario(idUsuario);
                    }
                });
            }
        });

        adapter.setOnDeleteClickListener(new UsuarioListAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Lógica para eliminar un usuario
                Usuario usuario = usuarios.get(position);
                eliminarUsuario(usuario.getId());
                adapter.notifyDataSetChanged();
            }
        });
    }

    // Método para actualizar un usuario
    private void actualizarUsuario(int idUsuario) {
        String nombre = etlNombre.getText().toString().trim();
        String email = etlEmail.getText().toString().trim();

        if (!nombre.isEmpty() && !email.isEmpty()) {
            // Actualización de los datos del usuario en la base de datos
            usuarioDAO.actualizarUsuario(idUsuario, nombre, email);
            Toast.makeText(MainActivity.this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
            etlNombre.setText("");
            etlEmail.setText("");
            btnAgregar.setText("Agregar");
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    agregarUsuario();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
        // Actualización de la lista de usuarios después de la actualización
        actualizarListaUsuarios();
    }

    // Método para eliminar un usuario
    private void eliminarUsuario(int idUsuario) {
        // Eliminación del usuario de la base de datos
        usuarioDAO.eliminarUsuario(idUsuario);
        Toast.makeText(MainActivity.this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
        // Actualización de la lista de usuarios después de eliminar uno
        actualizarListaUsuarios();
    }

    // Método para actualizar la lista de usuarios
    private void actualizarListaUsuarios() {
        mostrarUsuarios();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cierre de la conexión con la base de datos al destruir la actividad
        usuarioDAO.cerrar();
    }
}