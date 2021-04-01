package com.example.myapplication

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SmartLighting : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var brightness: Sensor? = null
    private lateinit var text: TextView
    private lateinit var pb: CircularProgressBar
    private var runAgain = 0;
    val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-2-default-rtdb.firebaseio.com/")
    val data1 = database1.getReference("PI_12_CONTROL")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_lighting)


        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener {
            val intent = Intent(this, SmartBuilding::class.java)
            startActivity(intent)
        }

        //text=findViewById(R.id.tv_text)

        val offButton = findViewById<Button>(R.id.offButton)
        offButton.setOnClickListener{
            runAgain = 1;
            data1.child("lcdtxt").setValue("LIGHT IS OFFED!!") // Must 16 Characters
            data1.child("camera").setValue("1")
            data1.child("lcdbkR").setValue("10")
            data1.child("lcdbkG").setValue("10")
            data1.child("lcdbkB").setValue("10")
            data1.child("relay1").setValue("1")
            data1.child("relay2").setValue("1")
            data1.child("ledlgt").setValue("2")
            data1.child("oledsc").setValue("1")
            data1.child("buzzer").setValue("1")

            text.text = "Light Is OFF"
            //Build Dialog
            val builder = AlertDialog.Builder(this)
            //Set title for alert dialog
            builder.setTitle("Dialog")
            //Set message for alert dialog
            builder.setMessage("The Light Is OFF!!!")

            //performing positive action
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                //Toast.makeText(this, "New item added successfully!", Toast.LENGTH_SHORT).show()
            })

            //performing negative action
            builder.setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        //Toast.makeText(this, "Cancelled Add New Item!", Toast.LENGTH_SHORT).show()
                    })

            //Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            //set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        val onButton = findViewById<Button>(R.id.onButton)
        onButton.setOnClickListener{
            //runAgain = 1;
            data1.child("lcdtxt").setValue("LIGHT IS ON !!! ") // Must 16 Characters
            data1.child("camera").setValue("1")
            data1.child("lcdbkR").setValue("10")
            data1.child("lcdbkG").setValue("10")
            data1.child("lcdbkB").setValue("10")
            data1.child("relay1").setValue("1")
            data1.child("relay2").setValue("1")
            data1.child("ledlgt").setValue("2")
            data1.child("oledsc").setValue("1")
            data1.child("buzzer").setValue("1")


            //Build Dialog
            val builder = AlertDialog.Builder(this)
            //Set title for alert dialog
            builder.setTitle("Dialog")
            //Set message for alert dialog
            builder.setMessage("The Light Is ON!!!")

            //performing positive action
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                //Toast.makeText(this, "New item added successfully!", Toast.LENGTH_SHORT).show()
            })

            //performing negative action
            builder.setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        //Toast.makeText(this, "Cancelled Add New Item!", Toast.LENGTH_SHORT).show()
                    })

            //Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            //set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        val offButton2 = findViewById<Button>(R.id.offButton2)
        offButton2.setOnClickListener{
            runAgain = 1;
            data1.child("lcdtxt").setValue("Auto Mode IS OFFED!!") // Must 16 Characters
            data1.child("camera").setValue("1")
            data1.child("lcdbkR").setValue("10")
            data1.child("lcdbkG").setValue("10")
            data1.child("lcdbkB").setValue("10")
            data1.child("relay1").setValue("1")
            data1.child("relay2").setValue("1")
            data1.child("ledlgt").setValue("2")
            data1.child("oledsc").setValue("1")
            data1.child("buzzer").setValue("1")
            text.text = "Auto Mode Is OFF"
            //Build Dialog
            val builder = AlertDialog.Builder(this)
            //Set title for alert dialog
            builder.setTitle("Dialog")
            //Set message for alert dialog
            builder.setMessage("The Auto Mode Is OFF!!!")

            //performing positive action
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                //Toast.makeText(this, "New item added successfully!", Toast.LENGTH_SHORT).show()
            })

            //performing negative action
            builder.setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        //Toast.makeText(this, "Cancelled Add New Item!", Toast.LENGTH_SHORT).show()
                    })

            //Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            //set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        val onButton2 = findViewById<Button>(R.id.onButton2)
        onButton2.setOnClickListener{
            runAgain = 0;
            data1.child("lcdtxt").setValue("Auto Mode IS ON !!! ") // Must 16 Characters
            data1.child("camera").setValue("1")
            data1.child("lcdbkR").setValue("10")
            data1.child("lcdbkG").setValue("10")
            data1.child("lcdbkB").setValue("10")
            data1.child("relay1").setValue("1")
            data1.child("relay2").setValue("1")
            data1.child("ledlgt").setValue("2")
            data1.child("oledsc").setValue("1")
            data1.child("buzzer").setValue("1")


            //Build Dialog
            val builder = AlertDialog.Builder(this)
            //Set title for alert dialog
            builder.setTitle("Dialog")
            //Set message for alert dialog
            builder.setMessage("The Auto Mode Is ON!!!")

            //performing positive action
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                //Toast.makeText(this, "New item added successfully!", Toast.LENGTH_SHORT).show()
            })

            //performing negative action
            builder.setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        //Toast.makeText(this, "Cancelled Add New Item!", Toast.LENGTH_SHORT).show()
                    })

            //Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            //set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        text = findViewById(R.id.tv_text)
        pb = findViewById(R.id.circularProgressBar)

        setUpSensorStuff()
    }
    private fun setUpSensorStuff() {

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event?.sensor?.type == Sensor.TYPE_LIGHT && runAgain == 0) {
            val light1 = event.values[0]

            text.text = "Sensor: $light1\n${brightness(light1)}"
            pb.setProgressWithAnimation(light1)
        }

    }

    private fun brightness(brightness: Float): String {
        var bright : String? = null
        bright = when (brightness.toInt()){
            0 -> {
                        data1.child("lcdtxt").setValue("Pitch black     ")
                        data1.child("relay1").setValue("1")
                        data1.child("relay2").setValue("1")
                        data1.child("camera").setValue("1")
                        data1.child("lcdbkR").setValue("10")
                        data1.child("lcdbkG").setValue("10")
                        data1.child("lcdbkB").setValue("10")
                        data1.child("ledlgt").setValue("2")
                        data1.child("buzzer").setValue("1")
                        "Pitch black"
            }

            in 1..10 -> {
                        data1.child("lcdtxt").setValue("Dark Environment")
                        data1.child("relay1").setValue("1")
                        data1.child("relay2").setValue("1")
                        data1.child("camera").setValue("1")
                        data1.child("lcdbkR").setValue("10")
                        data1.child("lcdbkG").setValue("10")
                        data1.child("lcdbkB").setValue("10")
                        data1.child("ledlgt").setValue("2")
                        data1.child("buzzer").setValue("1")
                        "Dark"
            }

            in 11..50 -> {
                        data1.child("lcdtxt").setValue("Slightly bright ")
                        data1.child("relay1").setValue("0")
                        data1.child("relay2").setValue("0")
                        data1.child("lcdbkR").setValue("10")
                        data1.child("lcdbkG").setValue("10")
                        data1.child("lcdbkB").setValue("10")
                        data1.child("camera").setValue("1")
                        data1.child("ledlgt").setValue("2")
                        data1.child("buzzer").setValue("1")
                        "Slightly bright"
            }

            in 51..5000 ->  {
                        data1.child("lcdtxt").setValue("Normal Bright   ")
                        data1.child("relay1").setValue("0")
                        data1.child("relay2").setValue("1")
                        data1.child("lcdbkR").setValue("10")
                        data1.child("lcdbkG").setValue("10")
                        data1.child("lcdbkB").setValue("10")
                        data1.child("camera").setValue("1")
                        data1.child("ledlgt").setValue("2")
                        data1.child("buzzer").setValue("1")
                        "Normal Bright"
            }

            in 5001..25000 -> {
                        data1.child("lcdtxt").setValue("IncrediblyBright")
                        data1.child("relay1").setValue("0")
                        data1.child("relay2").setValue("1")
                        data1.child("lcdbkR").setValue("10")
                        data1.child("lcdbkG").setValue("10")
                        data1.child("lcdbkB").setValue("10")
                        data1.child("camera").setValue("1")
                        data1.child("ledlgt").setValue("2")
                        data1.child("buzzer").setValue("1")
                        "Incredibly bright"
            }
            else -> {
                "Do not need light"
            }
        }
        return bright
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
