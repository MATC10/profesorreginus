package com.matc.profesorreginus

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class SixthActivity : AppCompatActivity() {
    private lateinit var wheel: ImageView
    private lateinit var arrow: ImageView
    var rotation = 0
    var rotationSpeed = 5
    val stopPosition = intArrayOf(720, 780, 840, 900, 960, 1020) //900 = contraseña 100
    val winPoints = intArrayOf(50, 10, 20, 100, 90, 70)
    var randPosition = 0
    var isSpinning = false
    private lateinit var playButton: ImageButton
    private lateinit var pauseButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sixth)

        val rootView = findViewById<ViewGroup>(R.id.clase2Layout)

        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)

        rootView.isClickable = true
        rootView.setOnClickListener { }

        wheel = findViewById(R.id.wheel)
        arrow = findViewById(R.id.arrow)


        playButton.setOnClickListener {
            //detener la música antes de iniciarla nuevamente
            val stopIntent = Intent(this, MusicService::class.java)
            stopIntent.action = "PAUSE"
            startService(stopIntent)

            //iniciar la música
            val playIntent = Intent(this, MusicService::class.java)
            playIntent.action = "PLAY"
            playIntent.putExtra("SONG_RESOURCE_ID", R.raw.puzzles)
            startService(playIntent)
        }

        pauseButton.setOnClickListener {
            val pauseIntent = Intent(this, MusicService::class.java)
            pauseIntent.action = "PAUSE"
            startService(pauseIntent)
        }


        val incorrectPasswordText = findViewById<TextView>(R.id.incorrectPasswordText)
        incorrectPasswordText.visibility = View.GONE //hacer invisible al inicio

        val nextActivityButton = findViewById<ImageButton>(R.id.nextActivityButton)
        nextActivityButton.isClickable = true

        val retryButton = findViewById<ImageButton>(R.id.retryButton)
        retryButton.isClickable = true

        arrow.setOnClickListener {
            if (!isSpinning) {
                randPosition = Random().nextInt(6)
                startSpin()
            }
        }
    }

    private fun startSpin() {
        isSpinning = true
        Handler().postDelayed({
            wheel.rotation = rotation.toFloat()


            when {
                rotation >= 500 -> rotationSpeed = 2
                rotation >= 400 -> rotationSpeed = 3
                rotation >= 300 -> rotationSpeed = 4
            }

            rotation += rotationSpeed
            if (rotation >= stopPosition[randPosition]) {

                showPopup(winPoints[randPosition].toString())
                isSpinning = false
            } else {
                startSpin()
            }
        }, 1)
    }

    private fun showPopup(points: String) {
        val incorrectPasswordText = findViewById<TextView>(R.id.incorrectPasswordText)
        val retryButton = findViewById<ImageButton>(R.id.retryButton)
        val nextActivityButton = findViewById<ImageButton>(R.id.nextActivityButton)

        if (points == "100" || points == "50") {
            incorrectPasswordText.text = "¡HAS TENIDO SUERTE, SALINI ESTÁ DENTRO!"
            incorrectPasswordText.visibility = View.VISIBLE
            retryButton.visibility = View.GONE
            nextActivityButton.visibility = View.VISIBLE
            nextActivityButton.setOnClickListener {
                val intent = Intent(this, FifthActivity::class.java)
                startActivity(intent)
            }
        } else {
            incorrectPasswordText.text = "NO HAS TENIDO SUERTE, VUELVE A INTENTARLO"
            incorrectPasswordText.visibility = View.VISIBLE
            retryButton.visibility = View.VISIBLE
            retryButton.setOnClickListener {
                incorrectPasswordText.visibility = View.GONE //hacer invisible antes de iniciar el giro
                retryButton.visibility = View.GONE

                rotation = 0
                rotationSpeed = 5
                randPosition = 0
                if (!isSpinning) {
                    randPosition = Random().nextInt(6)
                    startSpin()
                }
            }
        }
        nextActivityButton.isClickable = nextActivityButton.visibility == View.VISIBLE
        retryButton.isClickable = retryButton.visibility == View.VISIBLE
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
        intent.putExtra("SONG_RESOURCE_ID", R.raw.puzzles)
        startService(intent)
    }

    override fun onBackPressed() {
        val stopIntent = Intent(this, MusicService::class.java)
        stopIntent.action = "PAUSE"
        startService(stopIntent)
        super.onBackPressed()
    }

}