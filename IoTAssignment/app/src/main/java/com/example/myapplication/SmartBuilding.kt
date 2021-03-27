package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView

class SmartBuilding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_building)

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val cctvBtn = findViewById<ImageView>(R.id.cctv_camera)
        cctvBtn.setOnClickListener{
            val intent = Intent(this, cctv::class.java)
            startActivity(intent)
        }

        val smartDoorBtn = findViewById<ImageButton>(R.id.smart_door)
        smartDoorBtn.setOnClickListener{
            val intent = Intent(this, SmartDoor::class.java)
            startActivity(intent)
        }
    }
}