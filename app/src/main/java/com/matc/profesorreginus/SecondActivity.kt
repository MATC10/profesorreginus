package com.matc.profesorreginus

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class SecondActivity : AppCompatActivity() {
    private lateinit var rootLayout : ConstraintLayout
    private lateinit var cuadrodialogo : ImageView
    private lateinit var tvSecond : TextView
    private lateinit var iv2Reginus : ImageView
    private lateinit var iv2Pakorus : ImageView
    private lateinit var iv2Carrison : ImageView
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var btnLeft2: ImageButton
    private lateinit var btnRight2: ImageButton
    private lateinit var btnLeftSalini: ImageButton
    private lateinit var playButton: ImageButton
    private lateinit var pauseButton: ImageButton



    private var indiceTexto = 0
    private var indiceDialogo = 0
    private var dialogos = listOf(
        "ALTAVOCES: ¡¡ATENCIÓN!! !!ATENCIÓN!! El director del centro ha desaparecido en extrañas condiciones",
        "¡Rogamos que guarden la calma! Os mantendremos informados ante nuevas noticias.",
        "PROFESOR REGINUS: ¿Has oído eso Pakérus?, es posible que al director le haya pasado algo...",
        "PAKÉRUS: Sí, esto es perfecto, podré dar latigazos a mis alumnos libremente cuando no me entreguen las tareas.",
        "PROFESOR REGINUS: ¿No crees que deberíamos hacer algo? Tenemos que saber lo que ha pasado.",
        "PAKÉRUS: Por supuesto Reginus, si me entero de cualquier cosa te lo diré. Ahora tengo que ir a comprar un látigo.",
        "PAKÉRUS: Hasta luego."
    )
    private var texto = dialogos[indiceDialogo]

    private var dialogos2 = listOf(
        "PROFESOR REGINUS: Tengo que seguir mi investigación.",
        "PAKÉRUS: ¡¡¡¡IMAGÍNATE QUE ERES UN GOBLIN, COJO MI CUCHILLO, COJO MI ESCUDO, AAAHHHHHHH!!!!",
        "PAKÉRUS: Perdón, vengo de dar clase y ya he perdido el hilo de todo...",
        "PROFESOR REGINUS: Se te ve cansado ¿no has dormido bien?",
        "PAKÉRUS: He tenido una pesadilla horrible sobre tortugas ninja, espero que nunca se vuelva a repetir.",
        "PAKÉRUS: Tengo que irme, aún me quedan un par de alumnos que suspender.",
        "CÁRRISON: ¿Qué tal Reginus? hoy tengo mucho trabajo y ahora tengo una reunión ¿tienes una carpeta vacía para dejarme?",
        "CÁRRISON: Solamente quiero llevar la carpeta para parecer profesional, ya sabes, además de serlo hay que parecerlo.",
        "CÁRRISON: ¡Ah, una cosa! creo que Salini tiene alguna pista sobre el director, ve a ver si está en su clase.",
        "CÁRRISON: Tendrás que probar suerte si quieres hablar con él ¡está casi más solicitado que tú!",
        " "
        )
    private var texto2 = dialogos2[indiceDialogo]


    private var dialogos3 = listOf(
        "CÁRRISON: ¿Qué tal Reginus? hoy tengo mucho trabajo y ahora tengo una reunión ¿tienes una carpeta vacía para dejarme?",
        "CÁRRISON: Solamente quiero llevar la carpeta para parecer profesional, ya sabes, además de serlo hay que parecerlo.",
        "CÁRRISON: ¡Ah, una cosa! creo que Salini tiene alguna pista sobre el director, ve a verlo, está en su clase.",
        ""
    )
    private var texto3 = dialogos3[indiceDialogo]


    private var booleanOffice : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        rootLayout = findViewById(R.id.rootLayout)

        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)



        playButton.setOnClickListener {
            //detener la música antes de iniciarla nuevamente
            val stopIntent = Intent(this, MusicService::class.java)
            stopIntent.action = "PAUSE"
            startService(stopIntent)

            //iniciar la música
            val playIntent = Intent(this, MusicService::class.java)
            playIntent.action = "PLAY"
            startService(playIntent)
        }

        pauseButton.setOnClickListener {
            val pauseIntent = Intent(this, MusicService::class.java)
            pauseIntent.action = "PAUSE"
            startService(pauseIntent)
        }

        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        rootLayout.startAnimation(slideInAnimation)

        cuadrodialogo = findViewById(R.id.cuadrodialogo)
        tvSecond = findViewById(R.id.tvSecond)

        //INICIALIZAMOS Y PONEMOS INVISIBLE
        iv2Reginus = findViewById(R.id.iv2Reginus)
        iv2Pakorus = findViewById(R.id.iv2Pakorus)
        iv2Carrison = findViewById(R.id.iv2Carrison)
        btnLeft2 = findViewById(R.id.btnLeft2)
        btnRight2 = findViewById(R.id.btnRight2)
        btnLeftSalini = findViewById(R.id.btnLeftSalini)
        iv2Reginus.visibility = ImageView.INVISIBLE
        iv2Pakorus.visibility = ImageView.INVISIBLE
        iv2Carrison.visibility = ImageView.INVISIBLE
        btnLeftSalini.visibility = ImageView.INVISIBLE
        btnLeft2.visibility = ImageView.INVISIBLE
        btnRight2.visibility = ImageView.INVISIBLE

        booleanOffice = intent.getBooleanExtra("booleanOffice", false)

        if (booleanOffice) {
            iv2Reginus.visibility = ImageView.VISIBLE

            val slidePakorus = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_left)
            iv2Pakorus.startAnimation(slidePakorus)
            iv2Pakorus.visibility = ImageView.VISIBLE

            dialogo2()


        } else {
            dialogo()

            btnRight2.setOnClickListener {
                val intent = Intent(this, ThirdActivity::class.java)
                startActivity(intent)
            }
            btnLeft2.setOnClickListener {
                val intent = Intent(this, FourthActivity::class.java)
                startActivity(intent)
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
        // Reanudar el servicio de música
        val playIntent = Intent(this, MusicService::class.java)
        playIntent.action = "PLAY"
        startService(playIntent)
    }

    override fun onBackPressed() {
        val stopIntent = Intent(this, MusicService::class.java)
        stopIntent.action = "PAUSE"
        startService(stopIntent)
        super.onBackPressed()
    }


    private fun dialogo(){ //dialogo antes de trues
        handler = Handler(Looper.getMainLooper())

        runnable = object : Runnable {
            override fun run() {
                if (indiceTexto < texto.length) {
                    tvSecond.append(texto[indiceTexto].toString())
                    indiceTexto++
                    handler.postDelayed(this, 25)
                }
            }
        }

        handler.post(runnable)
        rootLayout.setOnClickListener {
            if (indiceTexto < texto.length) {
                handler.removeCallbacks(runnable)
                tvSecond.text = texto
                indiceTexto = texto.length
            } else if (indiceDialogo < dialogos.lastIndex) {
                indiceDialogo++
                texto = dialogos[indiceDialogo]
                indiceTexto = 0
                tvSecond.text = ""
                handler.post(runnable)



                if (texto == "¡Rogamos que guarden la calma! Os mantendremos informados ante nuevas noticias.") {
                    iv2Reginus.visibility = ImageView.VISIBLE
                    iv2Pakorus.visibility = ImageView.VISIBLE

                    val slideReginus = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_right)
                    iv2Reginus.startAnimation(slideReginus)

                    val slidePakorus = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_left)
                    iv2Pakorus.startAnimation(slidePakorus)
                }
                if(texto == "PAKÉRUS: Hasta luego."){
                    val slideOutToLeft = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_left)
                    iv2Pakorus.startAnimation(slideOutToLeft)
                    iv2Pakorus.visibility = ImageView.INVISIBLE
                    btnLeft2.visibility = ImageView.VISIBLE
                    btnRight2.visibility = ImageView.VISIBLE
                    cuadrodialogo.visibility = ImageView.INVISIBLE
                    tvSecond.visibility = ImageView.INVISIBLE

                }

            }
        }
    }


    private fun dialogo2() {
        handler = Handler(Looper.getMainLooper())

        runnable = object : Runnable {
            override fun run() {
                if (indiceTexto < texto2.length) {
                    tvSecond.append(texto2[indiceTexto].toString())
                    indiceTexto++
                    handler.postDelayed(this, 25)
                }
            }
        }

        handler.post(runnable)
        rootLayout.setOnClickListener {
            if (indiceTexto < texto2.length) {
                handler.removeCallbacks(runnable)
                tvSecond.text = texto2
                indiceTexto = texto2.length
            } else if (indiceDialogo < dialogos2.lastIndex) {
                indiceDialogo++
                texto2 = dialogos2[indiceDialogo]
                indiceTexto = 0
                tvSecond.text = ""
                handler.post(runnable)
            }

            if (texto2 == "CÁRRISON: ¿Qué tal Reginus? hoy tengo mucho trabajo y ahora tengo una reunión ¿tienes una carpeta vacía para dejarme?") {

                val slideOutToLeft2 = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_left)
                iv2Pakorus.startAnimation(slideOutToLeft2)
                iv2Pakorus.visibility = ImageView.INVISIBLE

                val slideCarrison = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_left)
                iv2Carrison.startAnimation(slideCarrison)
                iv2Carrison.visibility = ImageView.VISIBLE
            }

            if (texto2 == " "){
                tvSecond.visibility = TextView.INVISIBLE
                cuadrodialogo.visibility = ImageView.INVISIBLE
                btnLeftSalini.visibility = ImageButton.VISIBLE

                btnLeftSalini.setOnClickListener {
                    val intent = Intent(this, SixthActivity::class.java)
                    startActivity(intent)
                }
            }

        }
    }


}