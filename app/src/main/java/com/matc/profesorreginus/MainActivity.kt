package com.matc.profesorreginus

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    private lateinit var tvIntro: TextView
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var indiceTexto = 0
    private var indiceDialogo = 0
    private val dialogos = listOf("WARNING!", "Todos los personajes que aparecen en esta aventura gráfica son ficticios", "Cualquier parecido con la realidad es pura coincidencia")
    private var texto = dialogos[indiceDialogo]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvIntro = findViewById(R.id.tvIntro)
        rootLayout = findViewById(R.id.rootLayout)

        dialogo()
    }

    private fun dialogo(){
        handler = Handler(Looper.getMainLooper())

        runnable = object : Runnable {
            override fun run() {
                if (indiceTexto < texto.length) {
                    // Agrega el siguiente carácter del texto a la vista de texto
                    tvIntro.append(texto[indiceTexto].toString())
                    indiceTexto++

                    // Programa la próxima ejecución de este Runnable
                    handler.postDelayed(this, 25) // 100 milisegundos de retraso
                }
            }
        }


        handler.post(runnable)


        rootLayout.setOnClickListener {
            if (indiceTexto < texto.length) {

                handler.removeCallbacks(runnable)
                tvIntro.text = texto
                indiceTexto = texto.length
            } else if (indiceDialogo < dialogos.lastIndex) {

                indiceDialogo++
                texto = dialogos[indiceDialogo]
                indiceTexto = 0
                tvIntro.text = ""
                handler.post(runnable)
            } else {

                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        }
    }





}