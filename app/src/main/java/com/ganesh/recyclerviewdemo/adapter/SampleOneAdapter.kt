package com.ganesh.recyclerviewdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ganesh.recyclerviewdemo.R
import com.ganesh.recyclerviewdemo.models.SampleModel
import com.ganesh.recyclerviewdemo.utils.AppUtils
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

import kotlin.collections.ArrayList



class SampleOneAdapter(val items : ArrayList<SampleModel>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_sample_one, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

        Picasso.with(context)
            .load(item.mainImageURL)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(holder.imageView,object : Callback {
                override fun onSuccess() {

                }

                override fun onError() {
                    Picasso.with(context)
                        .load(item.mainImageURL)
                        .into(holder.imageView)
                }
            })

        holder.txtCollectedOne.text = item.collectedValue.toString()
        holder.txtTotalOne.text = item.totalValue.toString()
        holder.txtTitle.text = item.title
        holder.txtDescription.text = item.shortDescription
        try {
            holder.txtEndIn.text = AppUtils.twoDateDiff(item.startDate,item.endDate)
        } catch (e: Exception) {
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val imageView = view.findViewById<ImageView>(R.id.imageView)
    val txtTitle = view.findViewById<TextView>(R.id.txtTitle)
    val txtDescription = view.findViewById<TextView>(R.id.txtDescription)
    val txtCollectedOne = view.findViewById<TextView>(R.id.txtCollectedOne)
    val txtTotalOne = view.findViewById<TextView>(R.id.txtTotalOne)
    val txtEndIn = view.findViewById<TextView>(R.id.txtEndIn)
}