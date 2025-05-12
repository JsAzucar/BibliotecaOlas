package com.sugardaddy.bibliotecaolas.ui.libros

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sugardaddy.bibliotecaolas.R
import com.sugardaddy.bibliotecaolas.data.model.Libro

class LibroAdapter(private val listaLibros: List<Libro>) :
    RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    class LibroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
        val txtTipo: TextView = itemView.findViewById(R.id.txtTipo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_libro, parent, false)
        return LibroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = listaLibros[position]
        holder.txtTitulo.text = libro.titulo
        holder.txtDescripcion.text = libro.descripcion
        holder.txtTipo.text = libro.tipo
    }

    override fun getItemCount(): Int = listaLibros.size
}
