package com.kaval.android.myapplication.data.model

data class Stock(
    val symbol: String,
    val timestamps: List<Long>,
    val opens: List<Float>,
    val closures: List<Float>,
    val highs: List<Float>,
    val lows: List<Float>,
    val volumes: List<Long>
) {

    /**
     * This method creates new Map with timestamp as key and performance of stock based on closure values
     */
    fun getClosuresTimeMap(): Map<Long, Float>{
        val firstPrice = closures.first()
       return timestamps.zip(closures.map { value -> (value-firstPrice)*100/firstPrice }).toMap()
    }


}