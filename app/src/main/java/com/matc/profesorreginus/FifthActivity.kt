package com.matc.profesorreginus

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class FifthActivity : AppCompatActivity() {
    private lateinit var clase2Layout : ConstraintLayout
    private lateinit var cuadrodialogo : ImageView
    private lateinit var tvTexto : TextView
    private lateinit var iv2Reginus : ImageView
    private lateinit var iv2Salini : ImageView
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var btnLeft2: ImageButton
    private var booleanOffice = false
    private var indiceTexto = 0
    private var indiceDialogo = 0
    private lateinit var playButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private val dialogos = listOf(
        "PROFESOR REGINUS: Buenos días Salini ¿qué tal tu día?",
        "SABIO SALINI: Estaba preparándome la clase, comenzaremos aprendiendo Java y terminaremos creando un reactor nuclear.",
        "PROFESOR REGINUS: Vaya, parece divertido, me gustaría quedarme a ver tu clase pero estoy investigando lo del director.",
        "SABIO SALINI: ¿El director? se ha ido de viaje para no volver, está en las Bahamas viviendo la vida tranquilamente.",
        "PROFESOR REGINUS: ¡Pero...! ¿cómo sabes eso?",
        "SABIO SALINI: Reginus, tú sabes todo lo que pasa aquí, deberías saber que con una caña y 3 pilas hackeé un satélite.",
        "SABIO SALINI: Con eso pude ver la localización del director.",
        "PROFESOR REGINUS: Está bien saberlo, ya estaba preocupado.",
        "SABIO SALINI: Por cierto, enhorabuena.",
        "PROFESOR REGINUS: ¿Enhorabuena por qué?",
        "SABIO SALINI: ¿Por qué va a ser? eres el nuevo director, lo hemos votado en el grupo del centro.",
        "PROFESOR REGINUS: Pero yo no quiero ser dir...",
        "SABIO SALINI: ¡¡¡ENHORABUENA!!!",
        ""
    )
    private var texto = dialogos[indiceDialogo]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)
        clase2Layout = findViewById(R.id.clase2Layout)

        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)

        btnLeft2 = findViewById(R.id.btnLeft2)
        btnLeft2.visibility = ImageButton.INVISIBLE

        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        clase2Layout.startAnimation(slideInAnimation)

        playButton.setOnClickListener {
            //detener la música antes de iniciarla nuevamente
            val stopIntent = Intent(this, MusicService::class.java)
            stopIntent.action = "PAUSE"
            startService(stopIntent)

            //iniciar la música
            val playIntent = Intent(this, MusicService::class.java)
            playIntent.action = "PLAY"
            playIntent.putExtra("SONG_RESOURCE_ID", R.raw.rine)
            startService(playIntent)
        }

        pauseButton.setOnClickListener {
            val pauseIntent = Intent(this, MusicService::class.java)
            pauseIntent.action = "PAUSE"
            startService(pauseIntent)
        }


        cuadrodialogo = findViewById(R.id.cuadrodialogo)
        tvTexto = findViewById(R.id.tvTexto)


        iv2Reginus = findViewById(R.id.iv2Reginus)
        iv2Salini = findViewById(R.id.iv2Salini)


        dialogo()
    }
    private fun dialogo(){
        handler = Handler(Looper.getMainLooper())

        runnable = object : Runnable {
            override fun run() {
                if (indiceTexto < texto.length) {
                    tvTexto.append(texto[indiceTexto].toString())
                    indiceTexto++
                    handler.postDelayed(this, 25)
                }
            }
        }

        handler.post(runnable)
        clase2Layout.setOnClickListener {
            if (indiceTexto < texto.length) {
                handler.removeCallbacks(runnable)
                tvTexto.text = texto
                indiceTexto = texto.length
            } else if (indiceDialogo < dialogos.lastIndex) {
                indiceDialogo++
                texto = dialogos[indiceDialogo]
                indiceTexto = 0
                tvTexto.text = ""
                handler.post(runnable)
            }

            if(texto == ""){
                btnLeft2.visibility = ImageButton.VISIBLE
                tvTexto.visibility = TextView.INVISIBLE
                cuadrodialogo.visibility = ImageView.INVISIBLE

                btnLeft2.setOnClickListener{
                    val intent = Intent(this, EighthActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Pausar el servicio de música
        val pauseIntent = Intent(this, MusicService::class.java)
        pauseIntent.action = "PAUSE"
        startService(pauseIntent)
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, MusicService::class.java)
        intent.action = "PLAY"
        intent.putExtra("SONG_RESOURCE_ID", R.raw.rine)
        startService(intent)
    }

    override fun onBackPressed() {
        val stopIntent = Intent(this, MusicService::class.java)
        stopIntent.action = "PAUSE"
        startService(stopIntent)
        super.onBackPressed()
    }

}
