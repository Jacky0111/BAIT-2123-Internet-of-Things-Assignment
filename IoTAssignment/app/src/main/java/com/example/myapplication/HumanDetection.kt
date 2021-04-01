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


class HumanDetection : AppCompatActivity() {
    private lateinit var cmDetectedtext: TextView
    private lateinit var numTextView: TextView
    private lateinit var pb: CircularProgressBar
    private var ultrasonic : String? = null

    private val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-default-rtdb.firebaseio.com/")
    val data1 = database1.getReference("PI_12_CONTROL")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_human_detection)

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, SmartStreet::class.java)
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

                    if(ultrasonic!!.toFloat() <= 50.0) {
                        count++
                        data1.child("ledlgt").setValue("1")
                        data1.child("lcdtxt").setValue("Total num ppl:$count ")
                        data1.child("camera").setValue("1")
                        numTextView.text = count.toString()
                    }
                    else {
                        data1.child("ledlgt").setValue("1")
                        data1.child("lcdtxt").setValue("Total num ppl:$count ")
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