package com.example.weatherappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.coderstrust.weatherapp.VolleySingleton
import com.example.weatherappkotlin.R
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnParseData:Button = findViewById(R.id.parseDataBtn)

        btnParseData.setOnClickListener {
            parseData()
        }
    }

    private fun parseData(){
        Log.i("Dhukse", "Dhukse")

        val url = "https://api.openweathermap.org/data/2.5/weather?q=Dhaka&appid=f04cb68f14361f5b0db57cb3cd585e60"

        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
            response ->
            try {
                Log.i("Response", response.toString())
                val jsonObject = response.getJSONObject("main")
                val currentTemp = jsonObject.getDouble("temp")
                Log.i("CurrentTemp", (currentTemp-273).toString())

                val weatherArrObject = response.getJSONArray("weather")
                val mainData = weatherArrObject.getJSONObject(0).getString("main")
                val descriptionData = weatherArrObject.getJSONObject(0).getString("description")
                Log.i("MainData", mainData.toString())
                Log.i("DescriptionData", descriptionData.toString())

            }catch (e:JSONException){
                Log.e("ParseErr", e.toString())
            }
        }, Response.ErrorListener {
            error ->
            Log.e("ResponseErr", error.toString())
        })

        VolleySingleton.getInstance(applicationContext).addToRequestQueue(request)
    }
}