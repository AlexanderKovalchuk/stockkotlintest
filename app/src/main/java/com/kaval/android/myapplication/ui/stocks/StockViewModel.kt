package com.kaval.android.myapplication.ui.stocks

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kaval.android.myapplication.data.model.Stock
import com.kaval.android.myapplication.data.repository.StockRepository
import com.kaval.android.myapplication.util.DateHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.random.Random

class StockViewModel(private val stockRepository: StockRepository) : ViewModel() {
    var showMonthlyData: Boolean = false;
    val stocksColorMap: LinkedHashMap<String, Int> = LinkedHashMap<String, Int>()
    val description: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val stocks: MutableLiveData<List<Stock>> by lazy {
        MutableLiveData<List<Stock>>()
    }

    init {
        GlobalScope.launch(Dispatchers.Main) {
            updateStockData()
        }
    }

    suspend fun loadWeeklyStocks(): List<Stock> {
        return GlobalScope.async(Dispatchers.IO) {
            stockRepository.getWeekltyStockData()
        }.await()
    }

    suspend fun loadMonthlyStocks(): List<Stock> {
        return GlobalScope.async(Dispatchers.IO) {
            stockRepository.getMonthlyStockData()
        }.await()
    }

    suspend fun toggleStockData() {
        showMonthlyData = !showMonthlyData
        updateStockData()
    }

    suspend fun updateStockData() {
        val loadedStocks = if (showMonthlyData)
            loadWeeklyStocks()
        else loadMonthlyStocks()
        stocks.setValue(loadedStocks)
        updateDescription()
    }

    /**
     * creates new description based on first and last timestamp in stock.timestamp list
     */
    fun updateDescription() {
        description.setValue(
            "from ${DateHelper().convertLongToTime(stocks.value?.first()?.timestamps?.first())} to ${
                DateHelper().convertLongToTime(stocks.value?.first()?.timestamps?.last())
            }"
        )
    }

    /**
     * Returns color for stock if exists, otherwise map new random color to stock and return it
     */
    fun provideColorForStock(stockName: String): Int{
        return stocksColorMap.get(stockName)?:generateNewColorForStock(stockName)
    }

    fun generateNewColorForStock(stockName: String): Int {
        val color= Color.argb(
            255,
            Random.nextInt(255),
            Random.nextInt(255),
            Random.nextInt(255)
        );
        stocksColorMap.put(stockName, color)
        return color
    }

}