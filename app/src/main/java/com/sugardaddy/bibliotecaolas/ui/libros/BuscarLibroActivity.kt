package com.sugardaddy.bibliotecaolas.ui.libros

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sugardaddy.bibliotecaolas.R
import com.sugardaddy.bibliotecaolas.data.network.RetrofitClient
import kotlinx.coroutines.launch

class BuscarLibroActivity : AppCompatActivity() {

    private lateinit var edtIdLibro: EditText
    private lateinit var btnBuscar: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var contenedorResultado: LinearLayout
    private lateinit var resTitulo: TextView
    private lateinit var resDescripcion: TextView
    private lateinit var resTipo: TextView
    private lateinit var resEnlace: TextView
    private lateinit var resImagen: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_libro)

        edtIdLibro = findViewById(R.id.edtIdLibro)
        btnBuscar = findViewById(R.id.btnBuscar)
        progressBar = findViewById(R.id.progressBar)

        contenedorResultado = findViewById(R.id.contenedorResultado)
        resTitulo = findViewById(R.id.resTitulo)
        resDescripcion = findViewById(R.id.resDescripcion)
        resTipo = findViewById(R.id.resTipo)
        resEnlace = findViewById(R.id.resEnlace)
        resImagen = findViewById(R.id.resImagen)

        btnBuscar.setOnClickListener {
            val id = edtIdLibro.text.toString().trim()

            if (id.isEmpty()) {
                Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            buscarLibroPorId(id)
        }
    }

    private fun buscarLibroPorId(id: String) {
        progressBar.visibility = View.VISIBLE
        contenedorResultado.visibility = View.GONE
        btnBuscar.isEnabled = false

        lifecycleScope.launch {
            try {
                val libro = RetrofitClient.libroApi.getById(id)
                resTitulo.text = libro.titulo
                resDescripcion.text = libro.descripcion
                resTipo.text = libro.tipo
                resEnlace.text = libro.enlace
                resImagen.text = libro.imagen

                contenedorResultado.visibility = View.VISIBLE
            } catch (e: Exception) {
                Toast.makeText(this@BuscarLibroActivity, "Libro no encontrado", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
                btnBuscar.isEnabled = true
            }
        }
    }
}
