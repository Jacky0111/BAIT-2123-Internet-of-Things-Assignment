package com.example.myapplication

import android.annotation.SuppressLint
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
    private lateinit var msg1: TextView
    private lateinit var msg2: TextView

    private var humidSensor: Sensor? = null
    private lateinit var status: ImageView

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


        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        humidSensor =  sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)

        msg1 = findViewById(R.id.humid_percent)
        msg2 = findViewById(R.id.tv_msg2)
        status = findViewById(R.id.soil_status)
        status.setImageResource(R.drawable.sprout)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_RELATIVE_HUMIDITY) {
            val humid1 = event.values[0]

            msg1.text = "%.2f".format(humid1)
            msg2.text = relativeHumidity(humid1)
        }
    }

        private fun relativeHumidity(humidSensor :Float): String {
            return when (humidSensor.toInt()) {
                in 0..49 -> {
                    displaySlotMsg1()
                    msg2.setTextColor(Color.parseColor("#0000FF"))
                    "Watering Plants"
                }
                in 50..79 -> {
                    displaySlotMsg2()
                    msg2.setTextColor(Color.parseColor("#253A4B"))
                    "Plant is healthy"

                }
                else -> {
                    displaySlotMsg3()
                    msg2.setTextColor(Color.parseColor("#FF0000"))
                    "Plant is too wet"
                }
            }
        }

    private fun displaySlotMsg1() {
        status.setImageResource(R.drawable.watering_plants)
        data1.child("buzzer").setValue("0")
        data1.child("lcdtxt").setValue("Watering Plants!")
        data1.child("camera").setValue("1")
        data1.child("lcdscr").setValue("1")
        data1.child("lcdbkR").setValue("10")
        data1.child("lcdbkG").setValue("10")
        data1.child("lcdbkB").setValue("50")
        data1.child("relay1").setValue("1")
        data1.child("relay2").setValue("0")
    }

    private fun displaySlotMsg2() {
        status.setImageResource(R.drawable.sprout)
        data1.child("lcdtxt").setValue("Plant is healthy")
        data1.child("camera").setValue("1")
        data1.child("buzzer").setValue("0")
        data1.child("lcdscr").setValue("1")
        data1.child("lcdbkR").setValue("0")
        data1.child("lcdbkG").setValue("10")
        data1.child("lcdbkB").setValue("0")
        data1.child("relay1").setValue("1")
        data1.child("relay2").setValue("1")

    }

    private fun displaySlotMsg3(){
        status.setImageResource(R.drawable.hydroponic)
        data1.child("lcdtxt").setValue("Draining water!!")
        data1.child("camera").setValue("1")
        data1.child("buzzer").setValue("1")
        data1.child("lcdscr").setValue("1")
        data1.child("lcdbkR").setValue("10")
        data1.child("lcdbkG").setValue("0")
        data1.child("lcdbkB").setValue("0")
        data1.child("relay1").setValue("0")
        data1.child("relay2").setValue("4")
        data1.child("ledlgt").setValue("2")
        data1.child("oledsc").setValue("1")
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