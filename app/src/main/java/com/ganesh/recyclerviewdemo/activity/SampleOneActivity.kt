package com.ganesh.recyclerviewdemo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ganesh.recyclerviewdemo.R
import com.androidnetworking.error.ANError
import com.androidnetworking.AndroidNetworking

import com.androidnetworking.common.Priority
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ganesh.recyclerviewdemo.adapter.SampleOneAdapter
import com.ganesh.recyclerviewdemo.constants.AppConstants
import com.ganesh.recyclerviewdemo.models.SampleModel
import com.ganesh.recyclerviewdemo.utils.AppUtils
import com.ganesh.recyclerviewdemo.utils.MyPreferences
import org.json.JSONObject
import java.util.concurrent.TimeUnit


class SampleOneActivity : AppCompatActivity() {

    private val TAG = "SampleOne"
    private lateinit var arraySampleOneList: ArrayList<SampleModel>
    private lateinit var recyclerView:RecyclerView
    private lateinit var myPreferences:MyPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_one)

        recyclerView = findViewById(R.id.recyclerView)
        myPreferences = MyPreferences.getInstance(this)
        if (AppUtils.checkInternetConnection(this))
            getResult()
        else{
            // write code to get value from preferences
            try {
                val resString = myPreferences.getData(AppConstants.RESPONSE)
                if (resString!!.isNotEmpty() && resString != "NA"){
                    val jsonObject = JSONObject(resString)
                    processResponse(jsonObject)
                }
            } catch (e: Exception) {
            }
        }
    }


    private fun getResult(){
        AndroidNetworking.get("http://test.chatongo.in/testdata.json")
            .setPriority(Priority.HIGH)
            .responseOnlyIfCached
            .setMaxStaleCacheControl(2,TimeUnit.HOURS)
            .setMaxAgeCacheControl(2,TimeUnit.HOURS)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    processResponse(response)
                }

                override fun onError(error: ANError) {
                    // handle error
                    Log.d(TAG, "onError: ")
                }
            })
    }

    /**
     * this function will process response JSONObject and will apply on the recyclerView
     */
    private fun processResponse(response: JSONObject){
        val status = response.getString("Status")
        if (status == "200"){

            myPreferences.saveData(AppConstants.RESPONSE,response.toString())
            arraySampleOneList = ArrayList()
            val dataArray = response.getJSONObject("data").getJSONArray("Records")
            for(index in 0 until dataArray.length()){
                val item = dataArray.getJSONObject(index)
                val sampleModel = SampleModel(item.getInt("Id"),item.getString("title"))
                sampleModel.shortDescription = item.getString("shortDescription")
                sampleModel.collectedValue = item.getInt("collectedValue")
                sampleModel.totalValue = item.getInt("totalValue")
                sampleModel.startDate = item.getString("startDate")
                sampleModel.endDate = item.getString("endDate")
                sampleModel.mainImageURL = item.getString("mainImageURL")
                arraySampleOneList.add(sampleModel)
            }

            if (arraySampleOneList.size>0){
                recyclerView.apply {
                    recyclerView.isNestedScrollingEnabled = false
                    recyclerView.setItemViewCacheSize(8)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.layoutManager = LinearLayoutManager(this@SampleOneActivity)
                    adapter = SampleOneAdapter(arraySampleOneList,this@SampleOneActivity)
                }
            }
        }else{
            try {
                val resString = myPreferences.getData(AppConstants.RESPONSE)
                if (resString!!.isNotEmpty() && resString != "NA"){
                    val jsonObject = JSONObject(resString)
                    processResponse(jsonObject)
                }
            } catch (e: Exception) {
            }

        }
    }
}
