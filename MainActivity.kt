package com.example.chanluliao_project1

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.net.Uri
import android.os.AsyncTask
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.opencsv.CSVReader
import java.io.InputStreamReader

// remember to add the dependencies " implementation("com.opencsv:opencsv:5.5") "

class MainActivity : AppCompatActivity() {

    // 1. create button and textView for calculating the Heart Rate
    private lateinit var heartButton: Button
    private lateinit var heartTextView: TextView
    private val videoUri: Uri by lazy {
        // if you're using another video, please check the pixel range and threshold in SlowTask
        Uri.parse("android.resource://com.example.chanluliao_project1/" + R.raw.fingerflash)
    }
    //val videoUri = Uri.parse("android.resource://com.example.chanluliao_project1/" + R.raw.hr)

    /********************************************************************************************/
    // 2. create button and textView for calculating the Respiratory Rate Sensing
    private lateinit var respButton: Button
    private lateinit var respTextView: TextView

    /********************************************************************************************/
    // 3. create button to upload HB & RR to DB
    private lateinit var sentdataButton: Button
    private lateinit var db_helper1: SQLiteHelper
    var HRresult: Int = 0
    var RRresult: Int = 0
    /********************************************************************************************/
    // 4. create button to forward to Page 2
    private lateinit var symptomButton:Button
    /********************************************************************************************/
    // Main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // First. Create Function for measuring HeartRate
        heartButton = findViewById(R.id.heartButton)
        heartTextView = findViewById(R.id.heartTextView)

        heartButton.setOnClickListener {
            MeasuringHeartRate()
            Toast.makeText(this, "Calculating, please wait for seconds...", Toast.LENGTH_LONG).show()
        }

        // Second. Create Function for measuring RespRate
        respButton = findViewById(R.id.respButton)
        respTextView = findViewById(R.id.respTextView)

        respButton.setOnClickListener {
            val inputCSVfile = resources.openRawResource(R.raw.new19)
            val reader = CSVReader(InputStreamReader(inputCSVfile))
            val accelX = mutableListOf<Float>()
            val accelY = mutableListOf<Float>()
            val accelZ = mutableListOf<Float>()

            // Read CSV data and extract Z-axis values
            reader.readAll().forEach { row ->
                val zValue = row[0].toFloat()
                val yValue = row[1].toFloat()
                val xValue = row[2].toFloat()
                accelX.add(xValue)
                accelY.add(yValue)
                accelZ.add(zValue)
            }
            val respRateResult = calculateRespRate(accelX,accelY,accelZ)
            respTextView.text = "Respiratory Rate: $respRateResult BPM"
            RRresult = respRateResult.toInt()
        }

        // Third. Upload DataBase
        db_helper1 = SQLiteHelper(this)
        sentdataButton = findViewById(R.id.sentdataButton)
        sentdataButton.setOnClickListener {
            //db_helper.updateRating(selectedItem, symptomRating)
            db_helper1.updateHRandRR(SQLiteHelper.COLUMN_HEART,HRresult)
            db_helper1.updateHRandRR(SQLiteHelper.COLUMN_RESPIRATORY,RRresult)
            Toast.makeText(this, "Great! Upload Successfully", Toast.LENGTH_LONG).show()
        }

        // Forth. Forward to Activity2
        symptomButton = findViewById(R.id.symptomButton)
        symptomButton.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity((intent))
        }

    }

    private fun MeasuringHeartRate() {
        val slowTask = SlowTask(videoUri, this)
        slowTask.execute()
        //var Heartbeat = computeRate(videoUri, this)
        //resultTextView.text = "Heart Rate : $Heartbeat bpm
    }

    fun updateHeartRate(result: String) {
        heartTextView.text = "Heart Rate : $result bpm"
        HRresult = result.toInt()
    }
    
}
