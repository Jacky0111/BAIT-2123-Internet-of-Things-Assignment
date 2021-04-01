package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class smartDoorSecurity : AppCompatActivity() {

    private lateinit var cmDetectedtext: TextView
    private lateinit var numTextView: TextView
    private lateinit var pb: CircularProgressBar
    private var ultrasonic : String? = null
    val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-2-default-rtdb.firebaseio.com/")
    val data1 = database1.getReference("PI_12_CONTROL")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_door_security)

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, SmartDoor::class.java)
            startActivity(intent)
        }

        cmDetectedtext = findViewById(R.id.cmDetected)
        numTextView = findViewById(R.id.number)
        pb = findViewById(R.id.circularProgressBar)

        //val currentDateTime = LocalDateTime.now()
        val zoneId: ZoneId = ZoneId.of("Asia/Kuala_Lumpur")

        val formatterDate = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formattedDate = LocalDateTime.now(zoneId).format(formatterDate)

        val hour = LocalDateTime.now(zoneId).hour //+ 8
        var strHour = hour.toString()

        if(hour in 0..9) {
            strHour = "0".plus(hour.toString())
        }

        val fetchDatabaseRef = FirebaseDatabase.getInstance("https://bait2123-202101-12-2-default-rtdb.firebaseio.com/").reference.child("PI_12_$formattedDate")
        val lastQuery = fetchDatabaseRef.child(strHour).orderByKey().limitToLast(1)

        var count = 0

        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (snap in snapshot.children ) {
                    ultrasonic = snap.child("rand1").value.toString()
                    Log.i("firebase", snap.value.toString())
                    Log.i("sound", "$ultrasonic")

                    cmDetectedtext.text = ultrasonic
                    pb.setProgressWithAnimation(ultrasonic!!.toFloat())

                    if(ultrasonic!!.toFloat() >= 40.0) {
                        count++
                        data1.child("relay1").setValue("1")
                        data1.child("relay2").setValue("1")
                        data1.child("lcdbkR").setValue("10")
                        data1.child("lcdbkG").setValue("10")
                        data1.child("lcdbkB").setValue("10")
                        data1.child("camera").setValue("1")
                        data1.child("ledlgt").setValue("2")
                        data1.child("buzzer").setValue("1")
                        data1.child("lcdtxt").setValue("Detect& Captured")
                        data1.child("camera").setValue("1")
                        numTextView.text = count.toString()
                    }
                    else {
                        data1.child("relay1").setValue("0")
                        data1.child("relay2").setValue("1")
                        data1.child("ledlgt").setValue("2")
                        data1.child("lcdbkR").setValue("10")
                        data1.child("lcdbkG").setValue("10")
                        data1.child("lcdbkB").setValue("10")
                        data1.child("buzzer").setValue("0")
                        data1.child("lcdtxt").setValue("Nothing Detected")
                        data1.child("camera").setValue("1")
                        numTextView.text = count.toString()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        lastQuery.addValueEventListener(postListener)
    }
}