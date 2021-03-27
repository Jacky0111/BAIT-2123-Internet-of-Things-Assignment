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
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class InGround : AppCompatActivity(), SensorEventListener{

    private lateinit var sensorManager: SensorManager
    private var proxy : Sensor? = null
    private lateinit var text: TextView
    private lateinit var pb: CircularProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_ground)

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, SmartParking::class.java)
            startActivity(intent)
        }

        text = findViewById(R.id.tv_text)
        pb = findViewById(R.id.circularProgressBar)

        setUpSensorStuff()
    }

    private fun setUpSensorStuff() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proxy = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            val proxy1 = event.values[0]

            text.text = "Sensor: $proxy1\n${proximity(proxy1)}"
            //if (proxy1 == 0)
            pb.setProgressWithAnimation(proxy1)
        }

    }

    private fun proximity(proxy: Float): String {
        return when (proxy.toInt()) {
            0 -> "Pitch black"
            else -> "This light will blind you"
        }
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