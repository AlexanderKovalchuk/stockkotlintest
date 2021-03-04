package com.kaval.android.myapplication.ui.stocks

import CustomMarker
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.kaval.android.myapplication.R
import com.kaval.android.myapplication.data.model.Stock
import com.kaval.android.myapplication.util.DateHelper
import org.koin.androidx.viewmodel.ext.android.viewModel


class StocksActivity : AppCompatActivity() {
    private lateinit var lineChart: LineChart
    private lateinit var switchStocksButton: Button
    private val stockViewModel: StockViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stcoks)
        title = resources.getString(R.string.app_name)
        lineChart = findViewById<LineChart>(R.id.lineChartView)
        switchStocksButton = findViewById<Button>(R.id.switchStocksButton)
        switchStocksButton.setOnClickListener() {
            stockViewModel.toggleStockData()
        }
        stockViewModel.stocks.observe(this, Observer<List<Stock>> {
            switchStocksButton.setText(
                if (stockViewModel.showMonthlyData) resources.getString(R.string.monthly) else resources.getString(
                    R.string.weekly
                )
            )
            updateLineChart()
        })
        stockViewModel.description.observe(this, Observer<String> {
            updateDescription()
        })
        initializeUI()
    }

    private fun initializeUI() {
        initializeLineChart();
        val markerView = CustomMarker(this, R.layout.marker_view)
        lineChart.marker = markerView
    }


    fun initializeLineChart() {
        lineChart.xAxis.labelRotationAngle = 0f
        lineChart.xAxis.labelCount = 5
        lineChart.xAxis.isEnabled = false
        lineChart.axisRight.isEnabled = false
//        lineChart.xAxis.axisMaximum += 0.1f
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.setNoDataText(resources.getString(R.string.noDataText))
    }

    private fun updateLineChart() {
        val lines = arrayListOf<LineDataSet>()
        for (stock in stockViewModel.stocks.value!!) {
            val entries = ArrayList<Entry>()
            entries.addAll(
                stock.getClosuresMap().map { it -> Entry((it.key.toFloat()), it.value, it.key) })
            val vl = LineDataSet(entries, stock.symbol)
            vl.circleRadius = 1f
            vl.setDrawValues(false)
            vl.lineWidth = 3f
            vl.color = stockViewModel.provideColorForStok(stock.symbol)
            lines.add(vl)
        }
        lineChart.setData(LineData(lines as List<ILineDataSet>?))
        lineChart.invalidate()
    }

    private fun updateDescription() {
        lineChart.description.text = stockViewModel.description.value
        lineChart.invalidate()
    }

}