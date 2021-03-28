package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView

class SmartDoor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_door)

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, SmartBuilding::class.java)
            startActivity(intent)
        }

        val lockBtn = findViewById<ImageButton>(R.id.doorLock)
        lockBtn.setOnClickListener{

        }

        val unlockBtn = findViewById<ImageButton>(R.id.doorUnlock)
        unlockBtn.setOnClickListener{

        }
    }
}