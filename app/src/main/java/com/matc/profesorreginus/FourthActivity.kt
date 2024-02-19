package com.matc.profesorreginus

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class FourthActivity : AppCompatActivity() {
    private lateinit var classLayout : ConstraintLayout
    private lateinit var cuadrodialogo : ImageView
    private lateinit var tvTexto : TextView
    private lateinit var ivReginus : ImageView
    private lateinit var ivPharynha : ImageView
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var btnLeft: ImageButton
    private lateinit var btnLeftRecibidor: ImageButton
    private var booleanOffice = false
    private var booleanToOffice = false
    private var indiceTexto = 0
    private var indiceDialogo = 0
    private lateinit var playButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private val dialogos = listOf(
        "PHARYИHA: Привет, Регинус, есть ли у вас новые зацепки?",
        "PROFESOR REGINUS: Pharyиha ¿estás hablando de nuevo en ruso?",
        "PHARYИHA: Sorry, sometimes I get my language mixed up.",
        "PROFESOR REGINUS: ...",
        "PHARYИHA: ¡Vale, ya paro!",
        "PROFESOR REGINUS: Por cierto ¿por qué están todos los alumnos en el pasillo?",
        "PHARYИHA: Han llegado 30 segundos tarde, así que se quedan fuera, hoy doy clase conmigo mismo.",
        "PHARYИHA: ¿Y del director no se sabe nada? yo he escuchado un rumor por el pasillo.",
        "PHARYИHA: Pero iba con prisa y solamente pude escuchar la palabra 'periplo'.",
        "TELÉFONO DE PROFESOR REGINUS: Riiiiing, riiiiing",
        "PROFESOR REGINUS: Me tengo que ir Pharyиha, me están llamando 5 empresas distintas a la vez.",
        "PROFESOR REGINUS: Pero esta investigación debe continuar.",
        "*Tensión*"
    )
    private var texto = dialogos[indiceDialogo]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        classLayout = findViewById(R.id.classLayout)

        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)


        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        classLayout.startAnimation(slideInAnimation)


        playButton.setOnClickListener {
            //detener la música antes de iniciarla nuevamente
            val stopIntent = Intent(this, MusicService::class.java)
            stopIntent.action = "PAUSE"
            startService(stopIntent)

            //iniciar la música
            val playIntent = Intent(this, MusicService::class.java)
            playIntent.action = "PLAY"
            playIntent.putExtra("SONG_RESOURCE_ID", R.raw.bar)
            startService(playIntent)
        }

        pauseButton.setOnClickListener {
            val pauseIntent = Intent(this, MusicService::class.java)
            pauseIntent.action = "PAUSE"
            startService(pauseIntent)
        }


        cuadrodialogo = findViewById(R.id.cuadrodialogo)
        tvTexto = findViewById(R.id.tvTexto)

        //INICIALIZAMOS Y PONEMOS INVISIBLE
        ivReginus = findViewById(R.id.ivReginus)
        ivPharynha = findViewById(R.id.ivPharynha)
        btnLeft = findViewById(R.id.btnLeft)
        btnLeftRecibidor = findViewById(R.id.btnLeftRecibidor)
        btnLeft.visibility = ImageView.INVISIBLE
        btnLeftRecibidor.visibility = ImageView.INVISIBLE

        booleanOffice = intent.getBooleanExtra("booleanOffice", false)

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
        classLayout.setOnClickListener {
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

            if (texto == "*Tensión*") {
                if (booleanOffice) {
                    btnLeftRecibidor.visibility = ImageView.VISIBLE
                    btnLeftRecibidor.visibility = ImageView.VISIBLE
                    btnLeftRecibidor.setOnClickListener {
                        val intent = Intent(
                            this@FourthActivity, SecondActivity::class.java)  //enviamos el booleano
                        intent.putExtra("booleanOffice", booleanOffice)
                        startActivity(intent)
                    }

                } else {
                    btnLeft.visibility = ImageView.VISIBLE
                    cuadrodialogo.visibility = ImageView.INVISIBLE
                    tvTexto.visibility = TextView.INVISIBLE
                    btnLeft.setOnClickListener {
                        booleanToOffice = true
                        val intent = Intent(this@FourthActivity, ThirdActivity::class.java)  //enviamos el booleano
                        intent.putExtra("booleanToOffice", booleanToOffice)
                        startActivity(intent)
                    }
                }
                cuadrodialogo.visibility = ImageView.INVISIBLE
                tvTexto.visibility = ImageView.INVISIBLE
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
        intent.putExtra("SONG_RESOURCE_ID", R.raw.bar)
        startService(intent)
    }


    override fun onBackPressed() {
        val stopIntent = Intent(this, MusicService::class.java)
        stopIntent.action = "PAUSE"
        startService(stopIntent)
        super.onBackPressed()
    }

}
