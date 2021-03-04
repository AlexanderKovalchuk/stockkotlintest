package com.kaval.android.myapplication.ui.stocks

import CustomMarker
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.kaval.android.myapplication.R
import com.kaval.android.myapplication.data.model.Stock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class StocksActivity : AppCompatActivity() {
    private lateinit var lineChart: LineChart
    private lateinit var switchStocksButton: Button
    private val stockViewModel: StockViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stocks)
        title = resources.getString(R.string.app_name)
        lineChart = findViewById<LineChart>(R.id.lineChartView)

        switchStocksButton = findViewById<Button>(R.id.switchStocksButton)
        switchStocksButton.setOnClickListener() {
            GlobalScope.launch(Dispatchers.Main) {
                stockViewModel.toggleStockData()
            }
        }

        stockViewModel.stocks.observe(this, Observer<List<Stock>> {
            //toggle buttons text
            switchStocksButton.setText(
                if (stockViewModel.showMonthlyData)
                    resources.getString(R.string.monthly)
                else resources.getString(
                    R.string.weekly
                )
            )
            updateLineChart(stockViewModel.stocks.value!!)
        })

        stockViewModel.description.observe(this, Observer<String> {
            updateDescription(stockViewModel.description.value!!)
        })

        initializeUI()
    }

    private fun initializeUI() {
        initializeLineChart();
    }

    fun initializeLineChart() {
        lineChart.xAxis.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.setNoDataText(resources.getString(R.string.noDataText))
        val markerView = CustomMarker(this, R.layout.marker_view)
        lineChart.marker = markerView
    }

    private fun updateLineChart(stocks: List<Stock>) {
        val lines = arrayListOf<LineDataSet>()
        for (stock in stocks) {
            val entries = ArrayList<Entry>()
            entries.addAll(
                stock.getClosuresTimeMap()
                    .map { it -> Entry((it.key.toFloat()), it.value, it.key) })
            val vl = LineDataSet(entries, stock.symbol)
            vl.circleRadius = 1f
            vl.setDrawValues(false)
            vl.lineWidth = 3f
            vl.color = stockViewModel.provideColorForStock(stock.symbol)
            lines.add(vl)
        }
        lineChart.setData(LineData(lines as List<ILineDataSet>?))
        lineChart.invalidate()
    }

    private fun updateDescription(text: String) {
        lineChart.description.text = text
        lineChart.invalidate()
    }

}