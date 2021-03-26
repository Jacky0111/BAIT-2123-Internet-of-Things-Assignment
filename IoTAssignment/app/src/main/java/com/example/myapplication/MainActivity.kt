package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        class MainActivity : AppCompatActivity() {
            val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-default-rtdb.firebaseio.com/")
            val data1 = database1.getReference("PI_12_CONTROL")

            //data1.child("lcdtxt").setValue("Lim Ming Jun!!!!") // Must 16 Characters
            //data1.child("camera").setValue("1")
            //data1.child("lcdbkR").setValue("149")
            //data1.child("lcdbkG").setValue("37")
            //data1.child("lcdbkB").setValue("12")
            //data1.child("relay1").setValue("1")
            //data1.child("relay2").setValue("0")
            //data1.child("oledsc").setValue("1")
            //data1.child("buzzer").setValue("1")
            data1.child("relay2").setValue("0")
            data1.child("relay1").setValue("1")
            data1.child("camera").setValue("1")
            data1.child("lcdtxt").setValue("lyfsuccessfully!")
            data1.child("oledsc").setValue("1")
            data1.child("lcdbkR").setValue("150")
            data1.child("lcdbkG").setValue("50")
            data1.child("lcdbkB").setValue("255")
            data1.child("buzzer").setValue("1")

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