package com.example.myapplication

import android.annotation.SuppressLint
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
    private var distance: Sensor? = null
    private lateinit var defaultBrightness: TextView
    private lateinit var distanceTxt: TextView
    private lateinit var pb: CircularProgressBar
    private lateinit var dis_val: String

    private val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-default-rtdb.firebaseio.com/")
    private val data1 = database1.getReference("PI_12_CONTROL")

    @SuppressLint("SetTextI18n")
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
        distanceTxt = findViewById(R.id.dis_text)
        pb = findViewById(R.id.circularProgressBar)


        distanceTxt.text = "Distance: > 50m "

        data1.child("lcdbkR").setValue("255")
        data1.child("lcdbkG").setValue("255")
        data1.child("lcdbkB").setValue("0")
        data1.child("lcdtxt").setValue("Distance: > 50m ")
        data1.child("camera").setValue("1")

        setUpSensorStuff()
    }

    private fun setUpSensorStuff() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        distance = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            val light1 = event.values[0]

            dis_val = "%.2f".format(light1*10)
            distanceTxt.text = "Distance (m): %.2f".format(light1*10)
            defaultBrightness.text = "${brightness(light1)}"
            pb.setProgressWithAnimation(light1)
        }
    }

    private fun brightness(brightness: Float): String {
        var distanceComputed : String? = null
        distanceComputed = when (brightness.toInt()*10){
            in 0..50 -> {
                pushBrightnessData()
                "Brightness: 5000 lumens"
            }
            else -> {
                pushBrightnessData2()
                "Brightness: 4000 lumens"
            }
        }
        return distanceComputed;
    }

    private fun pushBrightnessData() {
        val m:String = "m"
        data1.child("ledlgt").setValue("1")
        data1.child("lcdbkR").setValue("255")
        data1.child("lcdbkG").setValue("255")
        data1.child("lcdbkB").setValue("0")
        data1.child("lcdtxt").setValue("Distance:$dis_val $m")
        data1.child("camera").setValue("1")
        //data1.child("lcdtxt").setValue("Distance: " + defaultBrightness.text.substring(14, defaultBrightness.text.indexOf("\n")) + "m")
    }

    private fun pushBrightnessData2() {
        val m:String = "m"
        data1.child("ledlgt").setValue("1")
        data1.child("lcdbkR").setValue("191")
        data1.child("lcdbkG").setValue("250")
        data1.child("lcdbkB").setValue("0")
        data1.child("lcdtxt").setValue("Distance:$dis_val $m")
        data1.child("camera").setValue("1")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        // Register a listener for the sensor.
        sensorManager.registerListener(this, distance, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}