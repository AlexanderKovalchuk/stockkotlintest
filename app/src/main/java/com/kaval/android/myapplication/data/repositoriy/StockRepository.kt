package com.kaval.android.myapplication.data.repositoriy

import com.kaval.android.myapplication.data.api.ApiHelper
import com.kaval.android.myapplication.data.model.Stock

class StockRepository(private val apiHelper: ApiHelper) {

    fun getWeekltyStockData(): List<Stock> {
        val stocks = apiHelper.getWeeklyStocks()
        if (!checkStocksIntegrity(stocks)) {
            throw IllegalStateException("stock data integrity fails")
        }
        return stocks
    }

    fun getMonthlyStockData(): List<Stock> {
        val stocks = apiHelper.getMonthlyStocks()
        if (!checkStocksIntegrity(stocks)) {
            throw IllegalStateException("stock data integrity fails")
        }
        return stocks
    }


    private fun checkStocksIntegrity(stocks: List<Stock>): Boolean {
        for (stock in stocks) {
            if (stock.closures.size != stock.timestamps.size)
                return false
        }
        return true
    }

}