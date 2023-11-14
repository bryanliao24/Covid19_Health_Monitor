package com.example.chanluliao_project1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Button
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var ratingBar: RatingBar
    private lateinit var upLoadButton: Button
    private lateinit var db_helper: SQLiteHelper
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        spinner = findViewById(R.id.spinner)
        ratingBar = findViewById(R.id.ratingBar)
        upLoadButton = findViewById(R.id.upLoadButton)
        db_helper = SQLiteHelper(this)
        sharedPrefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        val adapter = ArrayAdapter.createFromResource(this, R.array.symptoms_array, android.R.layout.simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // initialize to set the rating to default value
        // initializeAndUploadDefaultRatings()

        upLoadButton.setOnClickListener {

            val selectedItem = spinner.selectedItem.toString()
            val symptomRating = ratingBar.rating
            // SQLiteHelper.insertRating(selectedItem, symptomRating)
            sharedPrefs.edit().putFloat(selectedItem, symptomRating).apply()
            db_helper.updateSymptomRating(selectedItem, symptomRating)
            Toast.makeText(this, "Upload to Database Successfully", Toast.LENGTH_LONG).show()
        }
    }

    // ensure that close the database properly
    override fun onDestroy() {
        db_helper.close()
        super.onDestroy()
    }

//    private fun initializeAndUploadDefaultRatings() {
//        val items = resources.getStringArray(R.array.symptoms_array)
//        for (item in items) {
//            val defaultRating = 0.0F
//            sharedPrefs.edit().putFloat(item, defaultRating).apply()
//            db_helper.insertRating(item, defaultRating)
//        }
//    }
}