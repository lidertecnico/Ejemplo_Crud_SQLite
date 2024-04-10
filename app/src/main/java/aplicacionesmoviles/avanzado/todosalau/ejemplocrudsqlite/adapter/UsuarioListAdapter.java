package aplicacionesmoviles.avanzado.todosalau.ejemplocrudsqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.ejemplocrudsqlite.R;
import aplicacionesmoviles.avanzado.todosalau.ejemplocrudsqlite.model.Usuario;

public class UsuarioListAdapter extends BaseAdapter {
    private List<Usuario> usuarios; // Lista de usuarios a mostrar
    private LayoutInflater inflater; // Inflador de diseño para inflar vistas
    private OnEditClickListener editClickListener; // Escucha para clics en el botón de editar
    private OnDeleteClickListener deleteClickListener; // Escucha para clics en el botón de eliminar

    // Interfaz para escuchar clics en el botón de editar
    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    // Interfaz para escuchar clics en el botón de eliminar
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    // Métodos para establecer los listeners
    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    // Constructor
    public UsuarioListAdapter(Context context, List<Usuario> usuarios) {
        this.usuarios = usuarios;
        this.inflater = LayoutInflater.from(context);
    }

    // Devuelve el número de elementos en la lista
    @Override
    public int getCount() {
        return usuarios.size();
    }

    // Devuelve el usuario en la posición especificada
    @Override
    public Usuario getItem(int position) {
        return usuarios.get(position);
    }

    // Devuelve el ID del elemento en la posición especificada
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Método principal para crear y actualizar las vistas de cada elemento en la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) { // Si la vista no está inflada, inflarla
            convertView = inflater.inflate(R.layout.list_item_usuario, parent, false);
            viewHolder = new ViewHolder(); // Crear un nuevo ViewHolder para almacenar las vistas
            viewHolder.txtId = convertView.findViewById(R.id.etId); // Asignar vistas a los elementos del ViewHolder
            viewHolder.txtNombre = convertView.findViewById(R.id.etNombre);
            viewHolder.txtEmail = convertView.findViewById(R.id.etEmail);
            viewHolder.btnEditar = convertView.findViewById(R.id.btnEditar); // Asignar botón de editar
            viewHolder.btnEliminar = convertView.findViewById(R.id.btnEliminar); // Asignar botón de eliminar
            convertView.setTag(viewHolder); // Establecer el ViewHolder como una etiqueta de la vista
        } else {
            viewHolder = (ViewHolder) convertView.getTag(); // Si la vista ya está inflada, obtener el ViewHolder de la etiqueta
        }

        // Obtener el usuario en la posición actual
        Usuario usuario = usuarios.get(position);

        // Establecer los valores de los TextView con la información del usuario
        viewHolder.txtId.setText(String.valueOf("ID de usuario: " + usuario.getId()));
        viewHolder.txtNombre.setText("Nombre: "+usuario.getNombre());
        viewHolder.txtEmail.setText("Correo: "+usuario.getEmail());

        // Establecer listeners para los botones de editar y eliminar
        viewHolder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editClickListener != null) {
                    editClickListener.onEditClick(position);
                }
            }
        });

        viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(position);
                }
            }
        });

        return convertView; // Devolver la vista actualizada
    }

    // Clase estática para almacenar vistas de elementos de la lista
    static class ViewHolder {
        TextView txtId;
        TextView txtNombre;
        TextView txtEmail;
        Button btnEditar;
        Button btnEliminar;
    }
}
