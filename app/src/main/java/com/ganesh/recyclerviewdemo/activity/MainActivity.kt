package com.ganesh.recyclerviewdemo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ganesh.recyclerviewdemo.R
import android.os.Handler


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        Handler().postDelayed({
            // This method will be executed once the timer is over
            val intentSample1 = Intent(this@MainActivity, SampleOneActivity::class.java)
            startActivity(intentSample1)
            finish()
        }, 2000)


    }


}
