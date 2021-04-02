package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase

class IntruderDetection : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var proxy : Sensor? = null
    private lateinit var text: TextView
    private lateinit var msg1: TextView
    private lateinit var status1: ImageView

    private val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-default-rtdb.firebaseio.com/")
    private val data1 = database1.getReference("PI_12_CONTROL")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intruder_detection)

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, SmartAgriculture::class.java)
            startActivity(intent)
        }

        status1 = findViewById(R.id.intruder_status)
        status1.setImageResource(R.drawable.group_220)
        text = findViewById(R.id.tv_meterDetected)
        msg1 = findViewById(R.id.tv_msg1)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proxy = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            val proxy1 = event.values[0]

            text.text = "$proxy1"
            msg1.text = "${proximity(proxy1)}"
       }
    }

    private fun proximity(proxy: Float): String {
        return when (proxy.toInt()) {
            in 0..2 -> {
                displaySlotMsg2()
                msg1.setTextColor(Color.parseColor("#FF0000"))
                "Intruder Detected!!!"
            }
            else -> {
                displaySlotMsg1()
                msg1.setTextColor(Color.parseColor("#253A4B"))
                "Farm is safe"
            }
        }
    }

    private fun displaySlotMsg1() {
        status1.setImageResource(R.drawable.group_220)
        data1.child("buzzer").setValue("0")
        data1.child("lcdtxt").setValue("The farm is safe")
        data1.child("camera").setValue("1")
    }

    private fun displaySlotMsg2() {
        status1.setImageResource(R.drawable.group_219)
        data1.child("lcdtxt").setValue("Intruder Found!!")
        data1.child("camera").setValue("1")
        data1.child("buzzer").setValue("1")

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        // Register a listener for the sensor.
        sensorManager.registerListener(this, proxy, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}