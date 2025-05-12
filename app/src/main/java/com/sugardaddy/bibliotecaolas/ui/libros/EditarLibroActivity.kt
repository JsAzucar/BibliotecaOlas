package com.sugardaddy.bibliotecaolas.ui.libros

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sugardaddy.bibliotecaolas.R
import com.sugardaddy.bibliotecaolas.data.model.Libro
import com.sugardaddy.bibliotecaolas.data.network.RetrofitClient
import kotlinx.coroutines.launch

class EditarLibroActivity : AppCompatActivity() {

    private lateinit var edtIdLibro: EditText
    private lateinit var btnBuscar: Button
    private lateinit var edtTitulo: EditText
    private lateinit var edtDescripcion: EditText
    private lateinit var edtTipo: EditText
    private lateinit var edtEnlace: EditText
    private lateinit var edtImagen: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnEliminar: Button
    private lateinit var progressBar: ProgressBar

    private var libroActual: Libro? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_libro)

        // Referencias
        edtIdLibro = findViewById(R.id.edtIdLibro)
        btnBuscar = findViewById(R.id.btnBuscar)
        edtTitulo = findViewById(R.id.edtTitulo)
        edtDescripcion = findViewById(R.id.edtDescripcion)
        edtTipo = findViewById(R.id.edtTipo)
        edtEnlace = findViewById(R.id.edtEnlace)
        edtImagen = findViewById(R.id.edtImagen)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnEliminar = findViewById(R.id.btnEliminar)
        progressBar = findViewById(R.id.progressBar)

        // Eventos
        btnBuscar.setOnClickListener {
            val id = edtIdLibro.text.toString().trim()
            if (id.isNotEmpty()) cargarLibro(id)
            else Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
        }

        btnGuardar.setOnClickListener {
            val id = edtIdLibro.text.toString().trim()
            if (libroActual != null) actualizarLibro(id)
            else Toast.makeText(this, "Primero busque un libro", Toast.LENGTH_SHORT).show()
        }

        btnEliminar.setOnClickListener {
            val id = edtIdLibro.text.toString().trim()
            if (libroActual != null) confirmarEliminacion(id)
            else Toast.makeText(this, "Primero busque un libro", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarLibro(id: String) {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val libro = RetrofitClient.libroApi.getById(id)
                libroActual = libro
                edtTitulo.setText(libro.titulo)
                edtDescripcion.setText(libro.descripcion)
                edtTipo.setText(libro.tipo)
                edtEnlace.setText(libro.enlace)
                edtImagen.setText(libro.imagen)
            } catch (e: Exception) {
                Toast.makeText(this@EditarLibroActivity, "Libro no encontrado", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun actualizarLibro(id: String) {
        progressBar.visibility = View.VISIBLE
        btnGuardar.isEnabled = false

        val actualizado = Libro(
            id = id,
            titulo = edtTitulo.text.toString(),
            descripcion = edtDescripcion.text.toString(),
            tipo = edtTipo.text.toString(),
            enlace = edtEnlace.text.toString(),
            imagen = edtImagen.text.toString()
        )

        lifecycleScope.launch {
            try {
                RetrofitClient.libroApi.update(id, actualizado)
                Toast.makeText(this@EditarLibroActivity, "Libro actualizado", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@EditarLibroActivity, "Error al actualizar", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
                btnGuardar.isEnabled = true
            }
        }
    }

    private fun confirmarEliminacion(id: String) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de que deseas eliminar este libro?")
            .setPositiveButton("Sí") { _, _ -> eliminarLibro(id) }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarLibro(id: String) {
        progressBar.visibility = View.VISIBLE
        btnEliminar.isEnabled = false

        lifecycleScope.launch {
            try {
                RetrofitClient.libroApi.delete(id)
                Toast.makeText(this@EditarLibroActivity, "Libro eliminado", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@EditarLibroActivity, "Error al eliminar", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
                btnEliminar.isEnabled = true
            }
        }
    }
}
