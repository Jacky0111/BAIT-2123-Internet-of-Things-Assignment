package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase


class TemperatureHumidity : AppCompatActivity(),SensorEventListener {
    val API: String = "cdd963e46f0f99c0ea5bbbd0d3cb3165"
    private var textView: TextView? = null
    private var sensorManager: SensorManager? = null
    private var tempSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature_humidity)

        textView = findViewById(R.id.temperature_output)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        tempSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-default-rtdb.firebaseio.com/")
        val data1 = database1.getReference("PI_12_CONTROL")


        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener {
            val intent = Intent(this, SmartAgriculture::class.java)
            startActivity(intent)
        }
    }

    fun checkSensorAvailability(sensorType: Int): Boolean {
        var isSensor = false
        if (sensorManager!!.getDefaultSensor(sensorType) != null) {
            isSensor = true
        }
        return isSensor
    }



    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            val temp1 = event.values[0]

            text.text = "Sensor: $temp1\n${tempSensor(temp1)}"
            pb.setProgressWithAnimation(temp1)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, tempSensor,
                SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }


}

