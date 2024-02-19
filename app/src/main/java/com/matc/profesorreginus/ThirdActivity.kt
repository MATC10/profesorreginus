package com.matc.profesorreginus

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class ThirdActivity : AppCompatActivity() {
    private lateinit var officeLayout : ConstraintLayout
    private lateinit var cuadrodialogoOffice : ImageView
    private lateinit var tvOffice : TextView
    private lateinit var ivReginusOffice : ImageView
    private lateinit var cartaOffice : ImageView
    private lateinit var editTextCarta : EditText
    private lateinit var handler1: Handler
    private lateinit var runnable1: Runnable
    private lateinit var btnLeftOffice: ImageButton
    private lateinit var btnRightOffice: ImageButton
    private var booleanOffice = false
    private var booleanToOffice = false
    private var indiceTexto2 = 0
    private var indiceDialogo = 0
    private lateinit var playButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private val dialogos2 = listOf(
        "PROFESOR REGINUS: Necesito saber si hay alguna pista del director en su oficina, aún no quiero mover a mis contactos.",
        "PROFESOR REGINUS: La última vez que llamé a Bill Gates estaba ocupado echándole de comer a los patos de su casa.",
        "PROFESOR REGINUS: Mmmmm, parece que hay una nota sobre la mesa, voy a leerla.",
        "PROFESOR REGINUS: ¡ES UNA NOTA DE 10! ¿qué podrá significar?"
    )
    private var texto = dialogos2[indiceDialogo]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        officeLayout = findViewById(R.id.officeLayout)

        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)

        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        officeLayout.startAnimation(slideInAnimation)



        playButton.setOnClickListener {
            //detener la música antes de iniciarla nuevamente
            val stopIntent = Intent(this, MusicService::class.java)
            stopIntent.action = "PAUSE"
            startService(stopIntent)

            //iniciar la música
            val playIntent = Intent(this, MusicService::class.java)
            playIntent.action = "PLAY"
            playIntent.putExtra("SONG_RESOURCE_ID", R.raw.village)
            startService(playIntent)
        }

        pauseButton.setOnClickListener {
            val pauseIntent = Intent(this, MusicService::class.java)
            pauseIntent.action = "PAUSE"
            startService(pauseIntent)
        }

        cuadrodialogoOffice = findViewById(R.id.cuadrodialogoOffice)
        tvOffice = findViewById(R.id.tvOffice)

        //INICIALIZAMOS Y PONEMOS INVISIBLE
        editTextCarta = findViewById(R.id.editTextCarta)
        editTextCarta.inputType = InputType.TYPE_CLASS_NUMBER

        cartaOffice = findViewById(R.id.cartaOffice)
        ivReginusOffice = findViewById(R.id.ivReginusOffice)
        btnLeftOffice = findViewById(R.id.btnLeftOffice)
        btnRightOffice = findViewById(R.id.btnRightOffice)
        editTextCarta.visibility = ImageView.INVISIBLE
        cartaOffice.visibility = ImageView.INVISIBLE
        btnLeftOffice.visibility = ImageView.INVISIBLE
        btnRightOffice.visibility = ImageView.INVISIBLE

        //método para ocultar teclado
        fun hideKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        editTextCarta.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.toString() == "10") {

                    //ocultar teclado
                    hideKeyboard(this@ThirdActivity, editTextCarta)

                    ivReginusOffice.visibility = ImageView.VISIBLE
                    cuadrodialogoOffice.visibility = ImageView.VISIBLE
                    tvOffice.visibility = ImageView.VISIBLE

                    cartaOffice.visibility = ImageView.INVISIBLE
                    editTextCarta.visibility = ImageView.INVISIBLE


                    booleanOffice = true;


                    booleanToOffice = intent.getBooleanExtra("booleanToOffice", false)

                    if(booleanToOffice){//ir a recibidor
                        btnRightOffice.visibility = ImageView.VISIBLE
                        btnRightOffice.setOnClickListener {
                            val intent = Intent(this@ThirdActivity, SecondActivity::class.java)  //enviamos el booleano
                            intent.putExtra("booleanOffice", booleanOffice)
                            startActivity(intent)
                        }
                    }
                    else{ //ir a clase
                        btnLeftOffice.visibility = ImageView.VISIBLE
                        btnLeftOffice.setOnClickListener{
                            val intent = Intent(this@ThirdActivity, FourthActivity::class.java)
                            intent.putExtra("booleanOffice", booleanOffice)
                            startActivity(intent)
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //este método se llama para notificarte que, dentro de s, la cuenta de caracteres count comenzando en start está a punto de ser reemplazada por nuevo texto con longitud after
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //este método se llama para notificarte que, dentro de s, la cuenta de caracteres count comenzando en start acaba de reemplazar texto antiguo que tenía longitud before
            }
        })

        dialogo2()
    }

    private fun dialogo2(){
        handler1 = Handler(Looper.getMainLooper())

        runnable1 = object : Runnable {
            override fun run() {
                if (indiceTexto2 < texto.length) {
                    tvOffice.append(texto[indiceTexto2].toString())
                    indiceTexto2++
                    handler1.postDelayed(this, 25)
                }
            }
        }

        handler1.post(runnable1)

        officeLayout.setOnClickListener {
            if (indiceTexto2 < texto.length) {
                handler1.removeCallbacks(runnable1)
                tvOffice.text = texto
                indiceTexto2 = texto.length
            } else if (indiceDialogo < dialogos2.lastIndex) {
                indiceDialogo++
                texto = dialogos2[indiceDialogo]
                indiceTexto2 = 0
                tvOffice.text = ""
                handler1.post(runnable1)

                if(texto == "PROFESOR REGINUS: ¡ES UNA NOTA DE 10! ¿qué podrá significar?"){
                    ivReginusOffice.visibility = ImageView.INVISIBLE
                    cuadrodialogoOffice.visibility = ImageView.INVISIBLE
                    tvOffice.visibility = ImageView.INVISIBLE

                    cartaOffice.visibility = ImageView.VISIBLE
                    editTextCarta.visibility = ImageView.VISIBLE

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
        intent.putExtra("SONG_RESOURCE_ID", R.raw.village)
        startService(intent)
    }

    override fun onBackPressed() {
        val stopIntent = Intent(this, MusicService::class.java)
        stopIntent.action = "PAUSE"
        startService(stopIntent)
        super.onBackPressed()
    }



}