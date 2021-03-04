package com.kaval.android.myapplication.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getWeeklyStocks() = apiService.getWeeklyStocks()

    fun getMonthlyStocks() = apiService.getMonthlyStocks()

}