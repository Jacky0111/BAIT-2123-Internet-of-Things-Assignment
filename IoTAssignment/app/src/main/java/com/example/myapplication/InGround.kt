package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class InGround : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private var proxy : Sensor? = null
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_ground)

//        val backBtn = findViewById<ImageButton>(R.id.backButton)
//        backBtn.setOnClickListener{
//            val intent = Intent(this, SmartParking::class.java)
//            startActivity(intent)
//        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proxy = sensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

//    private fun proxy(proxy: Float): String {
//        return when (proxy.toInt()) {
//            0 -> "Hello LCC"
//            in 1..10 -> "Dark"
//            else -> "Slots are available"
//        }
//    }

//    override fun onSensorChanged(event: SensorEvent?) {
//        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
//            val yes = event.values[0]
//
//            text.text = "Sensor: $proxy\n${proxy(yes)}"
//        }
//    }

//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//        TODO("Not yet implemented")
//    }

//    override fun onResume() {
//        super.onResume()
//        sensorManager!!.registerListener(this, proxy, SensorManager.SENSOR_DELAY_NORMAL)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        sensorManager!!.unregisterListener(this)
//    }


}