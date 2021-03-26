package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-default-rtdb.firebaseio.com/")
        val data1 = database1.getReference("PI_12_CONTROL")

        data1.child("relay2").setValue("0")
        data1.child("relay1").setValue("1")
        data1.child("camera").setValue("1")
        data1.child("lcdtxt").setValue("lyfsuccessfully!")
        data1.child("oledsc").setValue("1")
        data1.child("lcdbkR").setValue("150")
        data1.child("lcdbkG").setValue("50")
        data1.child("lcdbkB").setValue("255")
        data1.child("buzzer").setValue("1")


    }
}