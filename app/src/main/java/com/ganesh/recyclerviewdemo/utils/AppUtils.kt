package com.ganesh.recyclerviewdemo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


object AppUtils{
    fun checkInternetConnection(context:Context):Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun twoDateDiff(startDate:String,endDate:String):String{

        try {
            //Dates to compare


            val date1: Date
            val date2: Date

            val dates = SimpleDateFormat("dd/MM/yyyy")

            //Setting dates
            date1 = dates.parse(startDate)
            date2 = dates.parse(endDate)

            //Comparing dates
            val difference = abs(date1.time - date2.time)
            val differenceDates = difference / (24 * 60 * 60 * 1000)

            return differenceDates.toString()

        } catch (exception: Exception) {

        }

        return ""

    }
}