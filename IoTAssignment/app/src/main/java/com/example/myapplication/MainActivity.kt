package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-2-default-rtdb.firebaseio.com/")
        val data1 = database1.getReference("PI_12_CONTROL")

        data1.child("lcdscr").setValue("1")
        data1.child("lcdbkR").setValue("10")
        data1.child("lcdbkG").setValue("10")
        data1.child("lcdbkB").setValue("10")

        val smartParkingBtn = findViewById<ImageView>(R.id.smartParkingButton)
        smartParkingBtn.setOnClickListener{
            val intent = Intent(this, SmartParking::class.java)
            startActivity(intent)
        }

        val smartStreetBtn = findViewById<ImageView>(R.id.smartStreetButton)
        smartStreetBtn.setOnClickListener{
            val intent = Intent(this, SmartStreet::class.java)
            startActivity(intent)
        }

        val smartAgricultureBtn = findViewById<ImageView>(R.id.smartAgriculture)
        smartAgricultureBtn.setOnClickListener{
            val intent = Intent(this, SmartAgriculture::class.java)
            startActivity(intent)
        }

        val smartBuildingBtn = findViewById<ImageView>(R.id.smartBuilding)
        smartBuildingBtn.setOnClickListener{
            val intent = Intent(this, SmartBuilding::class.java)
            startActivity(intent)
        }


    }
}