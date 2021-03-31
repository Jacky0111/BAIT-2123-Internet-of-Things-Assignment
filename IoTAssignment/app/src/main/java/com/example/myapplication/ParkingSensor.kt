package com.example.myapplication

import android.content.Intent
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
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ParkingSensor : AppCompatActivity() {
    private lateinit var text: TextView
    private lateinit var msg1: TextView
    private lateinit var status: ImageView
    private lateinit var pb: CircularProgressBar
    private var ultrasonic : String? = null

    val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-2-default-rtdb.firebaseio.com/")
    val data1 = database1.getReference("PI_12_CONTROL")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_sensor)

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, SmartParking::class.java)
            startActivity(intent)
        }

        text = findViewById(R.id.tv_cmDetected)
        msg1 = findViewById(R.id.display_msg1)
        status = findViewById(R.id.slot_status)
        pb = findViewById(R.id.circularProgressBar)

        val currentDateTime = LocalDateTime.now()

        val formatterDate = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formattedDate = currentDateTime.format(formatterDate)

        var hour = currentDateTime.hour + 8
//        val f: NumberFormat = DecimalFormat("00")
//        val hourTEMP = f.format(currentDateTime.hour)

        val fetchDatabaseRef = FirebaseDatabase.getInstance("https://bait2123-202101-12-2-default-rtdb.firebaseio.com/")
                .reference.child("PI_12_$formattedDate")

        var lastQuery = fetchDatabaseRef.child("$hour").orderByKey().limitToLast(1)
        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (snapshot in snapshot.children ){
                    ultrasonic = snapshot.child("rand1").getValue().toString()
                    Log.i("firebase", snapshot.getValue().toString())
                    Log.i("sound", "$ultrasonic")

                    text.setText(ultrasonic)
                    pb.setProgressWithAnimation(ultrasonic!!.toFloat())

                    if(ultrasonic!!.toFloat() <= 30.0) {
                        status.setImageDrawable(getResources().getDrawable(R.drawable.group_180))
                        msg1.text = "Slot is occupied"
                        data1.child("relay1").setValue("0")
                        data1.child("relay2").setValue("1")
                        data1.child("lcdtxt").setValue("Slot is occupied")
                        data1.child("camera").setValue("1")
                    }
                    else{
                        status.setImageDrawable(getResources().getDrawable(R.drawable.group_179))
                        msg1.text = "Slot is free now"
                        data1.child("relay1").setValue("1")
                        data1.child("relay2").setValue("1")
                        data1.child("lcdtxt").setValue("Slot is free now")
                        data1.child("camera").setValue("1")
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