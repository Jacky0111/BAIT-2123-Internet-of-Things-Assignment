package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class HumanDetection : AppCompatActivity() {
    private lateinit var text: TextView
    private lateinit var pb: CircularProgressBar
    private var ultrasonic : String? = null

    private val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-default-rtdb.firebaseio.com/")
    val data1 = database1.getReference("PI_12_CONTROL")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_human_detection)

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, LightProximity::class.java)
            startActivity(intent)
        }


    }
}