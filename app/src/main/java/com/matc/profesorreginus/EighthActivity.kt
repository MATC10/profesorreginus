package com.matc.profesorreginus

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EighthActivity : AppCompatActivity() {
    private lateinit var final_activity : LinearLayout
    private lateinit var reginusder2Layout : LinearLayout
    private lateinit var reginusder2TextView : TextView
    private lateinit var reginusder2ImageView : ImageView
    private lateinit var saliniLayout : LinearLayout
    private lateinit var saliniTextView : TextView
    private lateinit var saliniImageView : ImageView
    private lateinit var carrisonLayout : LinearLayout
    private lateinit var carrisonTextView : TextView
    private lateinit var carrisonImageView : ImageView
    private lateinit var pakerus2Layout : LinearLayout
    private lateinit var pakerus2TextView : TextView
    private lateinit var pakerus2ImageView : ImageView
    private lateinit var pharynha2Layout : LinearLayout
    private lateinit var pharynha2TextView : TextView
    private lateinit var pharynha2ImageView : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eigth)


        final_activity = findViewById(R.id.final_activity)
        reginusder2Layout = findViewById(R.id.reginusder2Layout)
        reginusder2TextView = findViewById(R.id.reginusder2TextView)
        reginusder2ImageView = findViewById(R.id.reginusder2ImageView)
        saliniLayout = findViewById(R.id.saliniLayout)
        saliniTextView = findViewById(R.id.saliniTextView)
        saliniImageView = findViewById(R.id.saliniImageView)
        carrisonLayout = findViewById(R.id.carrisonLayout)
        carrisonTextView = findViewById(R.id.carrisonTextView)
        carrisonImageView = findViewById(R.id.carrisonImageView)
        pakerus2Layout = findViewById(R.id.pakerus2Layout)
        pakerus2TextView = findViewById(R.id.pakerus2TextView)
        pakerus2ImageView = findViewById(R.id.pakerus2ImageView)
        pharynha2Layout = findViewById(R.id.pharynha2Layout)
        pharynha2TextView = findViewById(R.id.pharynha2TextView)
        pharynha2ImageView = findViewById(R.id.pharynha2ImageView)

        reginusder2TextView.setText("Profesor Reginus")
        saliniTextView.setText("Sabio Salini")
        carrisonTextView.setText("Cárrison")
        pakerus2TextView.setText("Pakérus")
        pharynha2TextView.setText("Pharyиha")

    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, MusicService::class.java)
        intent.action = "PLAY"
        intent.putExtra("SONG_RESOURCE_ID", R.raw.end)
        startService(intent)
    }

    override fun onBackPressed() {
        val stopIntent = Intent(this, MusicService::class.java)
        stopIntent.action = "PAUSE"
        startService(stopIntent)
        super.onBackPressed()
    }

}