package com.kaval.android.myapplication.data.api

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kaval.android.myapplication.data.model.Response
import com.kaval.android.myapplication.data.model.Stock
import com.kaval.android.myapplication.util.JsonLoader

class ApiServiceImpl(val context: Context) : ApiService {

    var WEEK_PATH = "week.json"
    var MONTH_PATH = "month.json"

    override fun getWeeklyStocks(): List<Stock> {
        return getStocksFromJson(context, WEEK_PATH)
    }

    override fun getMonthlyStocks(): List<Stock> {
        return getStocksFromJson(context, MONTH_PATH)
    }

    private fun getStocksFromJson(context: Context, filePath: String): List<Stock> {

        val jsonFileString =
            JsonLoader().loadJsonDataFromAsset(context.assets.open(filePath))
        val gson = Gson()
        val listPersonType = object : TypeToken<List<Response>>() {}.type
        var response = gson.fromJson(jsonFileString.trimMargin(), Response::class.java)
        if (response.status.equals("ok")) {
            return response.content.stocks;
        } else {
            //TODO implement State mldungen zum view update
            throw IllegalStateException("response status of downloaded file was not ok");
        }
    }

}