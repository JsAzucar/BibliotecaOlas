package com.sugardaddy.bibliotecaolas.ui.libros

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sugardaddy.bibliotecaolas.R
import com.sugardaddy.bibliotecaolas.data.model.Libro
import com.sugardaddy.bibliotecaolas.data.network.RetrofitClient
import kotlinx.coroutines.launch

class AgregarLibroActivity : AppCompatActivity() {

    private lateinit var edtTitulo: EditText
    private lateinit var edtDescripcion: EditText
    private lateinit var edtTipo: EditText
    private lateinit var edtEnlace: EditText
    private lateinit var edtImagen: EditText
    private lateinit var btnGuardar: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_libro)

        edtTitulo = findViewById(R.id.edtTitulo)
        edtDescripcion = findViewById(R.id.edtDescripcion)
        edtTipo = findViewById(R.id.edtTipo)
        edtEnlace = findViewById(R.id.edtEnlace)
        edtImagen = findViewById(R.id.edtImagen)
        btnGuardar = findViewById(R.id.btnGuardar)
        progressBar = findViewById(R.id.progressBar)

        btnGuardar.setOnClickListener {
            guardarLibro()
        }
    }

    private fun guardarLibro() {
        val titulo = edtTitulo.text.toString().trim()
        val descripcion = edtDescripcion.text.toString().trim()
        val tipo = edtTipo.text.toString().trim()
        val enlace = edtEnlace.text.toString().trim()
        val imagen = edtImagen.text.toString().trim()

        if (titulo.isEmpty() || descripcion.isEmpty() || tipo.isEmpty() || enlace.isEmpty() || imagen.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Mostrar progress y desactivar botón
        progressBar.visibility = View.VISIBLE
        btnGuardar.isEnabled = false

        val nuevoLibro = Libro(
            titulo = titulo,
            descripcion = descripcion,
            tipo = tipo,
            enlace = enlace,
            imagen = imagen
        )

        lifecycleScope.launch {
            try {
                RetrofitClient.libroApi.add(nuevoLibro)
                Toast.makeText(this@AgregarLibroActivity, "Libro guardado", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@AgregarLibroActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                progressBar.visibility = View.GONE
                btnGuardar.isEnabled = true
            }
        }
    }
}
