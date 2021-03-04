package com.kaval.android.myapplication.data.api

import com.kaval.android.myapplication.data.model.Stock

interface ApiService {

    fun getWeeklyStocks(): List<Stock>

    fun getMonthlyStocks(): List<Stock>
}
