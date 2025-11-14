package com.example.menubotones

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Ajustar los bordes
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //  Vínculos con los elementos del XML (correo contraseña etc)
        val correoTB = findViewById<EditText>(R.id.TextBox_Correo)
        val passwordTB = findViewById<EditText>(R.id.TextBox_contraseña)
        val botonLogin = findViewById<Button>(R.id.login_button)

        //  Evento al presionar el botón
        botonLogin.setOnClickListener {
            val correo = correoTB.text.toString()
            val password = passwordTB.text.toString()

            //  Condicion
            if (correo == "sunavij2@gmail.com" && password == "123456") {
                Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error, datos incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
