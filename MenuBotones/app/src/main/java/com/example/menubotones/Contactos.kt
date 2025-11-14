package com.example.menubotones

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Contactos : AppCompatActivity() {

    private val PERMISO_LECTURA_CONTACTOS = 1

    private lateinit var adapter: ArrayAdapter<String>

    private val listaCompletaContactos = mutableListOf<String>()
    private val listaParaMostrar = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactos)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            inicializarVistaYContactos()
        } else {
            solicitarPermiso()
        }
    }

    private fun solicitarPermiso() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            PERMISO_LECTURA_CONTACTOS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISO_LECTURA_CONTACTOS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                inicializarVistaYContactos()
            } else {
                Toast.makeText(this, "Permiso denegado.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inicializarVistaYContactos() {
        val listView = findViewById<ListView>(R.id.lista_de_contactos)
        val buscador = findViewById<SearchView>(R.id.buscador)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaParaMostrar)
        listView.adapter = adapter

        cargarContactosDelTelefono()

        buscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filtrarLista(newText.orEmpty())
                return true
            }
        })
    }

    private fun cargarContactosDelTelefono() {
        listaCompletaContactos.clear()

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null,
            null,
            "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} ASC"
        )

        cursor?.use {
            val indexNombre = it.getColumnIndex(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )
            val indexNumero = it.getColumnIndex(
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )

            val setUnicos = HashSet<String>()

            while (it.moveToNext()) {
                val nombre = it.getString(indexNombre)
                val numero = it.getString(indexNumero)

                val texto = "$nombre\n$numero"

                if (!setUnicos.contains(texto)) {
                    listaCompletaContactos.add(texto)
                    setUnicos.add(texto)
                }
            }
        }

        filtrarLista("")
    }

    private fun filtrarLista(texto: String) {
        listaParaMostrar.clear()

        if (texto.isEmpty()) {
            listaParaMostrar.addAll(listaCompletaContactos)
        } else {
            listaCompletaContactos.forEach {
                if (it.contains(texto, ignoreCase = true)) {
                    listaParaMostrar.add(it)
                }
            }
        }

        adapter.notifyDataSetChanged()
    }
}
