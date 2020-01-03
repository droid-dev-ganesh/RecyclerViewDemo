package com.ganesh.recyclerviewdemo.utils

import android.content.Context
import android.content.SharedPreferences

class MyPreferences private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences? =
        context.getSharedPreferences("MyDemoPref", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        val prefsEditor = sharedPreferences!!.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

    fun getData(key: String): String? {
        return if (sharedPreferences != null) {
            sharedPreferences.getString(key, "NA")
        } else "NA"
    }

    companion object {
        private var myPreferences: MyPreferences? = null

        fun getInstance(context: Context): MyPreferences {
            if (myPreferences == null) {
                myPreferences = MyPreferences(context)
            }
            return myPreferences!!
        }
    }
}
