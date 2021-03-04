package com.kaval.android.myapplication.ui.stocks

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaval.android.myapplication.data.model.Stock
import com.kaval.android.myapplication.data.repositoriy.StockRepository
import com.kaval.android.myapplication.util.DateHelper

class StockViewModel(private val stockRepository: StockRepository) : ViewModel() {
    val description: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    var showMonthlyData: Boolean = false;

    val stocks: MutableLiveData<List<Stock>> by lazy {
        MutableLiveData<List<Stock>>()
    }

    init {
        loadWeeklyStocks()
        updateDescription()
    }


    fun loadWeeklyStocks() {
        stocks.setValue(stockRepository.getWeekltyStockData())
        updateDescription()
        showMonthlyData = false
    }

    fun loadMonthlyStocks() {
        stocks.setValue(stockRepository.getMonthlyStockData())
        updateDescription()
        showMonthlyData = true
    }

    fun toggleStockData() {
        if (showMonthlyData)
            loadWeeklyStocks()
        else loadMonthlyStocks()

    }

    fun updateDescription() {
        description.setValue(
            "from ${DateHelper().convertLongToTime(stocks.value?.first()?.timestamps?.first())} to ${
                DateHelper().convertLongToTime(stocks.value?.first()?.timestamps?.last())
            }"
        )
    }

    fun provideColorForStok(symbol: String): Int {
        when (symbol) {
            "SPY" -> return Color.RED
            "MSFT" -> return Color.BLUE
            "AAPL" -> return Color.BLACK
            else -> return Color.GRAY
        }
    }

}