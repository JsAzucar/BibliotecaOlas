package com.sugardaddy.cafeteriaudb.data.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sugardaddy.cafeteriaudb.data.model.Usuario

object FirebaseRepository {

    private val dbRef = FirebaseDatabase.getInstance().reference

    private val db: DatabaseReference = FirebaseDatabase.getInstance().getReference("usuarios")


    fun guardarUsuario(uid: String, usuario: Usuario, onComplete: (Boolean) -> Unit = {}) {
        db.child(uid).setValue(usuario)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun obtenerUsuarioPorUID(uid: String, callback: (Usuario?) -> Unit) {
        db.child(uid).get().addOnSuccessListener { snapshot ->
            val usuario = snapshot.getValue(Usuario::class.java)
            callback(usuario)
        }.addOnFailureListener {
            callback(null)
        }
    }

    fun actualizarRol(uid: String, nuevoRol: String) {
        db.child(uid).child("rol").setValue(nuevoRol)
    }

    fun obtenerTodosLosUsuarios(callback: (List<Usuario>) -> Unit) {
        db.get().addOnSuccessListener { snapshot ->
            val lista = mutableListOf<Usuario>()
            snapshot.children.forEach { child ->
                val usuario = child.getValue(Usuario::class.java)
                if (usuario != null) {
                    lista.add(usuario)
                }
            }
            callback(lista)
        }.addOnFailureListener {
            callback(emptyList())
        }
    }

    fun obtenerUsuarioPorNombreOEmail(input: String, callback: (Usuario?, String?) -> Unit) {
        db.get().addOnSuccessListener { snapshot ->
            for (child in snapshot.children) {
                val usuario = child.getValue(Usuario::class.java)
                if (usuario != null &&
                    (usuario.nombre.equals(input, ignoreCase = true)
                            || usuario.correo.equals(input, ignoreCase = true))
                ) {
                    callback(usuario, child.key)
                    return@addOnSuccessListener
                }
            }
            callback(null, null)
        }.addOnFailureListener {
            callback(null, null)
        }
    }

    fun correoExisteEnDatabase(email: String, callback: (Boolean) -> Unit) {
        db.get().addOnSuccessListener { snapshot ->
            for (child in snapshot.children) {
                val usuario = child.getValue(Usuario::class.java)
                if (usuario != null && usuario.correo.equals(email, ignoreCase = true)) {
                    callback(true)
                    return@addOnSuccessListener
                }
            }
            callback(false)
        }.addOnFailureListener {
            callback(false)
        }
    }
}
