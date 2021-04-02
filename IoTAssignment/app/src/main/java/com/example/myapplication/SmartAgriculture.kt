package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView

class SmartAgriculture : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_agriculture)

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val soilBtn = findViewById<ImageView>(R.id.soil_humidity)
        soilBtn.setOnClickListener{
            val intent = Intent(this, SoilHumidity::class.java)
            startActivity(intent)
        }

        val tempHuBtn = findViewById<ImageButton>(R.id.intruder_detection)
        tempHuBtn.setOnClickListener{
            val intent = Intent(this, IntruderDetection::class.java)
            startActivity(intent)
        }
    }
}