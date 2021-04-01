package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class InGround : AppCompatActivity(), SensorEventListener{
    private lateinit var sensorManager: SensorManager
    private var proxy : Sensor? = null
    private lateinit var text: TextView
    private lateinit var msg1: TextView
    private lateinit var msg3: TextView
    private lateinit var status1: ImageView
    private lateinit var pb: CircularProgressBar
    var random : String? = null

    val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-default-rtdb.firebaseio.com/")
    val data1 = database1.getReference("PI_12_CONTROL")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_ground)

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, SmartParking::class.java)
            startActivity(intent)
        }

        text = findViewById(R.id.tv_text)
        msg1 = findViewById(R.id.display_msg1)
        msg3 = findViewById(R.id.display_msg3)
        status1 = findViewById(R.id.barrier_gate)
        pb = findViewById(R.id.circularProgressBar)

        setUpSensorStuff()

        val zoneId: ZoneId = ZoneId.of("Asia/Kuala_Lumpur")

        val formatterDate = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formattedDate = LocalDateTime.now(zoneId).format(formatterDate)

        //val formattedDate = currentDateTime.format(formatterDate)

        val hour = LocalDateTime.now(zoneId).hour //+ 8
        var strHour = hour.toString()

        if(hour in 0..9) {
            strHour = "0".plus(hour.toString())
        }

        val fetchDatabaseRef = FirebaseDatabase.getInstance("https://bait2123-202101-12-2-default-rtdb.firebaseio.com/")
                .reference.child("PI_12_$formattedDate")

        var lastQuery = fetchDatabaseRef.child(strHour).orderByKey().limitToLast(1)
        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (snapshot in snapshot.children ){
                    random = snapshot.child("rand1").getValue().toString()
                    Log.i("firebase", snapshot.getValue().toString())
                    Log.i("sound", random.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        lastQuery.addValueEventListener(postListener)
    }

    private fun setUpSensorStuff() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proxy = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY) {
            val proxy1 = event.values[0]

            text.text = "Sensor: $proxy1\n${proximity(proxy1)}"
            pb.setProgressWithAnimation(proxy1)
        }
    }

    private fun proximity(proxy: Float): String {
        return when (proxy.toInt()) {
            in 0..1 -> {
                displaySlotMsg1()
                "No Car Detected"
            }
            else -> {
                displaySlotMsg2()
                "Car Detected"
            }
        }
    }

    private fun displaySlotMsg1() {
        status1.setImageDrawable(getResources().getDrawable(R.drawable.group_192))
        msg1.text = "Barrier gate is closed"
        msg3.text = " "
        data1.child("lcdtxt").setValue("No Car Detected+")
        data1.child("camera").setValue("1")
    }

    private fun displaySlotMsg2() {
        status1.setImageDrawable(getResources().getDrawable(R.drawable.group_190))
        msg1.text = "Barrier gate is opened"
        msg3.text = "$random slots available"
        data1.child("lcdtxt").setValue("Car Detected++++")
        data1.child("camera").setValue("1")
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