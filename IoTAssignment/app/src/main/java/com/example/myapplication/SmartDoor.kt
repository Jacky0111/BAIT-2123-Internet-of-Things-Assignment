package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class SmartDoor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_door)

        val database1 = FirebaseDatabase.getInstance("https://bait2123-202101-12-default-rtdb.firebaseio.com/")
        val data1 = database1.getReference("PI_12_CONTROL")

        val backBtn = findViewById<ImageButton>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, SmartBuilding::class.java)
            startActivity(intent)
        }

        val lockBtn = findViewById<ImageButton>(R.id.doorlock)
        lockBtn.setOnClickListener{

            data1.child("lcdtxt").setValue("Door Is Locked!!")
            data1.child("relay1").setValue("0")
            data1.child("relay2").setValue("4")
            data1.child("camera").setValue("1")
            data1.child("ledlgt").setValue("2")
            data1.child("buzzer").setValue("1")
            //Build Dialog
            val builder = AlertDialog.Builder(this)
            //Set title for alert dialog
            builder.setTitle("Dialog")
            //Set message for alert dialog
            builder.setMessage("Door Is Locked !!!")

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

        val unlockBtn = findViewById<ImageButton>(R.id.doorUnlock)
        unlockBtn.setOnClickListener{


            data1.child("lcdtxt").setValue("Door Is Unlocked")
            data1.child("relay1").setValue("1")
            data1.child("relay2").setValue("1")
            data1.child("camera").setValue("1")
            data1.child("ledlgt").setValue("2")
            data1.child("buzzer").setValue("1")
            //Build Dialog
            val builder = AlertDialog.Builder(this)
            //Set title for alert dialog
            builder.setTitle("Dialog")
            //Set message for alert dialog
            builder.setMessage("Door Is Unlocked !!!")

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

        val sendButton = findViewById<Button>(R.id.sendButton)
        sendButton.setOnClickListener{
            val myDM = findViewById<EditText>(R.id.doorMessage)

            data1.child("lcdtxt").setValue(myDM.text.toString()) // Must 16 Characters
            data1.child("camera").setValue("1")
            data1.child("lcdbkR").setValue("10")
            data1.child("lcdbkG").setValue("10")
            data1.child("lcdbkB").setValue("10")
            data1.child("relay1").setValue("0")
            data1.child("relay2").setValue("0")
            data1.child("ledlgt").setValue("2")
            data1.child("oledsc").setValue("1")
            data1.child("buzzer").setValue("1")

            //Build Dialog
            val builder = AlertDialog.Builder(this)
            //Set title for alert dialog
            builder.setTitle("Dialog")
            //Set message for alert dialog
            builder.setMessage("Live Message is Sent!!!")

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

    }
}