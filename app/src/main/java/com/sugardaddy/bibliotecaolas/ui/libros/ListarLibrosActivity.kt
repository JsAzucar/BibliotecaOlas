package com.sugardaddy.bibliotecaolas.ui.libros

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.sugardaddy.bibliotecaolas.R
import com.sugardaddy.bibliotecaolas.data.model.Libro
import com.sugardaddy.bibliotecaolas.data.network.RetrofitClient
import kotlinx.coroutines.launch

class ListarLibrosActivity : AppCompatActivity() {

    private lateinit var recyclerLibros: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_libros)

        recyclerLibros = findViewById(R.id.recyclerLibros)

        obtenerLibros()
    }

    private fun obtenerLibros() {
        lifecycleScope.launch {
            try {
                val lista = RetrofitClient.libroApi.getAll()
                recyclerLibros.adapter = LibroAdapter(lista)
            } catch (e: Exception) {
                Toast.makeText(this@ListarLibrosActivity, "Error al cargar libros", Toast.LENGTH_SHORT).show()
                Log.e("ListarLibros", "Error: ${e.message}")
            }
        }
    }
}
