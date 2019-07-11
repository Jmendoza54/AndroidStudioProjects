package com.example.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Variables cuyo valor no puede ser modificado
    val PI = 3.14

    //Variable que permite modificar valor se puede declarar sin tipo de dato o con dato
    var cont = 0
    var saludo = "Hola"
    var i: Int = 5
    var nulo: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnContador.setOnClickListener { view ->
            cont++
            Toast.makeText(this,"Clock en contador: $cont", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
    }
}
