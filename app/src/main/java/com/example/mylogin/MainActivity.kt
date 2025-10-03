package com.example.mylogin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val emailEditText = findViewById<EditText>(R.id.editTextText)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val loginButton = findViewById<Button>(R.id.button)

        val email = "sunavij2@gmail.com"
        val password = "123456"
        loginButton.setOnClickListener {
            val emailI = emailEditText.text.toString().trim()
            val passwordI = passwordEditText.text.toString()

            if (emailI == email && passwordI == password) {
                Toast.makeText(this, "✅ Se inició sesión exitosamente", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "❌ No se pudo iniciar sesión", Toast.LENGTH_LONG).show()
            }
        }
    }
}
