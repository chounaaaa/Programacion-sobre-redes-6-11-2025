package com.example.menubotones

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class Sensores : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var texto: TextView

    private var aceleracionActual = 10f
    private var ultimaAceleracion = 10f
    private val UMBRAL_AGITACION = 15f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensores)

        texto = findViewById(R.id.el_texto_sensor)
        texto.text = "Mueve el móvil..."
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        val acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL)

        val proximidad = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        sensorManager.registerListener(this, proximidad, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(evento: SensorEvent?) {
        if (evento?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = evento.values[0]
            val y = evento.values[1]
            val z = evento.values[2]
            ultimaAceleracion = aceleracionActual
            aceleracionActual = sqrt(x * x + y * y + z * z)
            val diferencia = aceleracionActual - ultimaAceleracion
            if (diferencia > UMBRAL_AGITACION) {
                texto.text = "¡Teléfono Agitado!"
            }
        }

        if (evento?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            val distancia = evento.values[0]
            if (distancia < 5) {
                texto.text = "¡Objeto Cerca!"
            } else {
                texto.text = "Pasa la mano por encima..."
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }
}
