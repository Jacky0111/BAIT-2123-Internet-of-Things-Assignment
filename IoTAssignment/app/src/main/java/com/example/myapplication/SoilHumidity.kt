package com.example.myapplication

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.example.myapplication.R.*
import com.example.myapplication.R.id.soil_output
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.random.Random

class SoilHumidity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var random:String? = null
        private var textView: TextView? = null
        private var sensorManager: SensorManager? = null
        private var tempSensor: Sensor? = null

        val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-default-rtdb.firebaseio.com/")
        val data1 = database1.getReference("PI_12_CONTROL")

        super.onCreate(savedInstanceState)
        setContentView(layout.activity_soil_humidity)

        textView = findViewById(R.id.temperature_output)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        tempSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        val randomSoilHumidity = Random.nextDouble(from = 0.0, until = 100.0)

        val backBtn = findViewById<ImageButton>(id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, SmartAgriculture::class.java)
            startActivity(intent)
        }



    }
}