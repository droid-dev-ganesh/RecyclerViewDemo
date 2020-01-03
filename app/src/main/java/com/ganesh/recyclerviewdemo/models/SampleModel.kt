package com.ganesh.recyclerviewdemo.models


data class SampleModel(val id:Int,val title:String){
    var shortDescription:String = ""
    var collectedValue:Int = 0
    var totalValue:Int = 0
    var startDate:String = ""
    var endDate:String = ""
    var mainImageURL:String = ""
}