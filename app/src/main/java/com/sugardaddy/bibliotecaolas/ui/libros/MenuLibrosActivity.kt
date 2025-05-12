package com.sugardaddy.bibliotecaolas.ui.libros

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sugardaddy.bibliotecaolas.R
import android.widget.Button

class MenuLibrosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_libros)

        // Referencias a los botones
        val btnVerLibros = findViewById<Button>(R.id.btnVerLibros)
        val btnAgregarLibro = findViewById<Button>(R.id.btnAgregarLibro)
        val btnBuscarLibro = findViewById<Button>(R.id.btnBuscarLibro)
        val btnEditarLibro = findViewById<Button>(R.id.btnEditarLibro)

        // Eventos de clic
        btnVerLibros.setOnClickListener {
            startActivity(Intent(this, ListarLibrosActivity::class.java))
        }

        btnAgregarLibro.setOnClickListener {
            startActivity(Intent(this, AgregarLibroActivity::class.java))
        }

        btnBuscarLibro.setOnClickListener {
            startActivity(Intent(this, BuscarLibroActivity::class.java))
        }

        btnEditarLibro.setOnClickListener {
            startActivity(Intent(this, EditarLibroActivity::class.java))
        }
    }
}
