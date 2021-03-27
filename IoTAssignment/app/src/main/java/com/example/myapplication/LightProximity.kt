package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.FirebaseDatabase
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class LightProximity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var brightness: Sensor? = null
    private lateinit var defaultBrightness: TextView
    private lateinit var pb: CircularProgressBar

    private val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-default-rtdb.firebaseio.com/")
    val data1 = database1.getReference("PI_12_CONTROL")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_proximity)

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, SmartStreet::class.java)
            startActivity(intent)
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        defaultBrightness = findViewById(R.id.tv_text)
        pb = findViewById(R.id.circularProgressBar)

        data1.child("camera").setValue("1")
        data1.child("oledsc").setValue("1")
        data1.child("lcdbkR").setValue("255")
        data1.child("lcdbkG").setValue("255")
        data1.child("lcdbkB").setValue("0")
        data1.child("lcdtxt").setValue("Lim Ming Jun hii")
        data1.child("camera").setValue("1")

        setUpSensorStuff()
    }

    private fun setUpSensorStuff() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            val light1 = event.values[0]

            defaultBrightness.text = "Distance (m): $light1\n${brightness(light1)}"
            pb.setProgressWithAnimation(light1)
        }
    }

    private fun brightness(brightness: Float): String {
        var bright : String? = null
        bright = when (brightness.toInt()){
            in 5..10 -> {
                pushBrightnessData()
                "Brightness: 5000 lumens"
            }
            else -> {
                "Brightness: 4000 lumens"
            }
        }
        return bright;
    }

    private fun pushBrightnessData() {
        data1.child("oledsc").setValue("1")
        data1.child("lcdbkR").setValue("230")
        data1.child("lcdbkG").setValue("230")
        data1.child("lcdbkB").setValue("0")
        data1.child("lcdtxt").setValue("1234567891234567")
        data1.child("camera").setValue("1")
        //data1.child("lcdtxt").setValue("Distance: " + defaultBrightness.text.substring(14, defaultBrightness.text.indexOf("\n")) + "m")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        // Register a listener for the sensor.
        sensorManager.registerListener(this, brightness, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}