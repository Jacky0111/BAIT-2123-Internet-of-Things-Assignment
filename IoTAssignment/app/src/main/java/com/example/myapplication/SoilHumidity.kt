package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R.*
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class SoilHumidity : AppCompatActivity(),SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var humidLevel: TextView
    private var humidSensor: Sensor? = null
    private lateinit var humidOutput: TextView
    private lateinit var soilStatusImage: ImageView


    val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-default-rtdb.firebaseio.com/")
    val data1 = database1.getReference("PI_12_CONTROL")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(layout.activity_soil_humidity)

        val backBtn = findViewById<ImageButton>(id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, SmartAgriculture::class.java)
            startActivity(intent)
        }

        val waterButton = findViewById<Button>(R.id.water_plants_button)
        waterButton.setOnClickListener(){
//            data1.child("lcdtxt").setValue("Watering Plants!") // Must 16 Characters
//            data1.child("camera").setValue("1")
//            data1.child("lcdbkR").setValue("10")
//            data1.child("lcdbkG").setValue("10")
//            data1.child("lcdbkB").setValue("10")
//            data1.child("relay1").setValue("1")
//            data1.child("relay2").setValue("1")
//            data1.child("ledlgt").setValue("2")
//            data1.child("oledsc").setValue("1")
        }

        val stopButton = findViewById<Button>(R.id.stop_water_button)
        stopButton.setOnClickListener(){
//            data1.child("lcdtxt").setValue("Watering is Stop") // Must 16 Characters
//            data1.child("camera").setValue("1")
//            data1.child("lcdbkR").setValue("10")
//            data1.child("lcdbkG").setValue("10")
//            data1.child("lcdbkB").setValue("10")
//            data1.child("relay1").setValue("1")
//            data1.child("relay2").setValue("1")
//            data1.child("ledlgt").setValue("2")
//            data1.child("oledsc").setValue("1")
        }

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        humidSensor =  sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        humidOutput = findViewById(R.id.soilStatus)
        soilStatusImage = findViewById(R.id.plantImage)
        soilStatusImage.setImageResource(R.drawable.sprout)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_RELATIVE_HUMIDITY) {
            val humid1 = event.values[0]

            humidLevel.text = "$humid1"
            humidOutput.text = "${relativeHumidity(humid1)}"
        }
    }
        private fun relativeHumidity(humidSensor :Float): String{
            return when (humidSensor.toInt()) {
                in 0..50 -> {
                    displaySlotMsg1()
                    humidOutput.setTextColor(Color.parseColor("#FF0000"))
                    "Plant need water"
                }
                else -> {
                    displaySlotMsg2()
                    humidOutput.setTextColor(Color.parseColor("#253A4B"))
                    "Plant is healthy"
                }
            }
        }

    private fun displaySlotMsg1() {
        soilStatusImage.setImageResource(R.drawable.soil__1_)
//        data1.child("buzzer").setValue("0")
//        data1.child("lcdtxt").setValue("Plant need water")
//        data1.child("camera").setValue("1")
    }

    private fun displaySlotMsg2() {
        soilStatusImage.setImageResource(R.drawable.sprout)
//        data1.child("lcdtxt").setValue("Plant is healthy")
//        data1.child("camera").setValue("1")
//        data1.child("buzzer").setValue("1")

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        // Register a listener for the sensor.
        sensorManager.registerListener(this, humidSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}