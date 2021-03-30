package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class SmartStreet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_street)

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val proximityLightBtn = findViewById<ImageButton>(R.id.proximityLightBtn)
        proximityLightBtn.setOnClickListener{
            val intent = Intent(this, LightProximity::class.java)
            startActivity(intent)
        }

        val humanDetectionBtn = findViewById<ImageButton>(R.id.humanDetectionButton)
        humanDetectionBtn.setOnClickListener{
            val intent = Intent(this, HumanDetection::class.java)
            startActivity(intent)
        }

    }
}