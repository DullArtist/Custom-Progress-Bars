package com.app.charts.ui.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.charts.R
import com.app.charts.databinding.ActivityLineChartBinding
import com.app.charts.helpers.CustomMarkerView
import com.app.charts.helpers.Utilities
import com.app.charts.helpers.ChartType
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.tabs.TabLayout

class LineChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLineChartBinding
    private lateinit var context: Activity

    private val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    private val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    private val daysInMonth = (1..31).map { it.toString() }  // List of days (1 to 31)

    private val weeklyData = listOf(50, 48, 46, 47, 44, 40, 35)
    private val yearlyData = listOf(50, 47, 44, 44, 47, 43, 41, 36, 42, 45, 48, 52)
    private val monthlyData = listOf(50, 47, 44, 44, 45, 47, 43, 41, 36, 42, 45, 48, 52, 42, 45, 48, 52, 48, 52, 42, 45, 48, 52, 48, 52, 48, 52, 42, 45, 48, 52)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLineChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    private fun init() {
        initViews()
        initClickListeners()
    }
    
    private fun initViews() {
        context = this;

        setChart(weeklyData,days, ChartType.WEEKLY)

    }
    
    private fun initClickListeners() {
        binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when {
                    tab!!.position == 0 -> {
                        binding.weeklyChart.visibility = View.VISIBLE
                        binding.monthlyChart.visibility = View.GONE
                        binding.yearlyChart.visibility = View.GONE

                        setChart(weeklyData,days, ChartType.WEEKLY)
                    }
                    tab.position == 1 -> {
                        binding.weeklyChart.visibility = View.GONE
                        binding.monthlyChart.visibility = View.VISIBLE
                        binding.yearlyChart.visibility = View.GONE

                        setChart(monthlyData,daysInMonth, ChartType.MONTHLY)
                    }
                    tab.position == 2 -> {
                        binding.weeklyChart.visibility = View.GONE
                        binding.monthlyChart.visibility = View.GONE
                        binding.yearlyChart.visibility = View.VISIBLE

                        setChart(yearlyData,months, ChartType.YEARLY)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun setChart(data: List<Int>, xAxisData: List<String>, type: ChartType) {
        // Create entries for the LineChart
        val entries = getEntries(data)
        val lineDataSet = getLineDataSet(entries)

        val lineData = LineData(lineDataSet)

        val lineChart = when (type) {
            ChartType.WEEKLY -> {
                binding.weeklyChart
            }
            ChartType.MONTHLY -> {
                binding.monthlyChart
            }
            else -> {
                binding.yearlyChart
            }
        }

        lineChart.data = lineData

        setXAxis(xAxisData,type)
        setYAxis(type)

        setChartBehaviour(type)

        val markerView = CustomMarkerView(context, R.layout.item_marker_view)
        lineChart.marker = markerView

        if (type == ChartType.MONTHLY) {
            val monthIndex = Utilities.getMonth()
            lineChart.moveViewToX(monthIndex.toFloat())

        } else if (type == ChartType.YEARLY) {
            val monthIndex = Utilities.getMonth() + 1
            lineChart.moveViewToX(monthIndex.toFloat())
        }



        // Refresh the chart
        lineChart.invalidate()  // Refresh chart after data update


    }

    private fun setYAxis(type: ChartType, min: Float = 35f, max: Float = 60f, interval: Float = 5f, totalCount: Int = 6) {

        val lineChart = when (type) {
            ChartType.WEEKLY -> {
                binding.weeklyChart
            }
            ChartType.MONTHLY -> {
                binding.monthlyChart
            }
            else -> {
                binding.yearlyChart
            }
        }

        val yAxis = lineChart.axisLeft
        yAxis.axisMinimum = min  // Set minimum value for Y-axis
        yAxis.axisMaximum = max  // Set maximum value for Y-axis

        // Set the interval (granularity) between Y-axis values This sets the Y-axis increments to 5 unit (you can adjust this)
        yAxis.granularity = interval
        yAxis.setLabelCount(totalCount, true)

        yAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return "${value.toInt()}%"
            }
        }
    }

    private fun setXAxis(xAxisData: List<String>,type: ChartType) {

        val lineChart = when (type) {
            ChartType.WEEKLY -> {
                binding.weeklyChart
            }
            ChartType.MONTHLY -> {
                binding.monthlyChart
            }
            else -> {
                binding.yearlyChart
            }
        }

        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisData)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.granularity = 1f

        lineChart.setVisibleXRangeMaximum(6f)
    }

    private fun setChartBehaviour(type: ChartType) {

        val lineChart = when (type) {
            ChartType.WEEKLY -> {
                binding.weeklyChart
            }
            ChartType.MONTHLY -> {
                binding.monthlyChart
            }
            else -> {
                binding.yearlyChart
            }
        }

        lineChart.axisRight.isEnabled = false  // Disable right axis
        lineChart.description.isEnabled = false  // Disable description label
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(false)
        lineChart.setScaleEnabled(false)
        lineChart.isDragEnabled = true
        lineChart.legend.isEnabled = false
        lineChart.animateY(1000)
    }

    private fun getLineDataSet(entries: List<Entry>): LineDataSet {
        val lineDataSet = LineDataSet(entries, "Weight (kg)")
        lineDataSet.color = ContextCompat.getColor(context, R.color.blue)
        lineDataSet.valueTextColor = ContextCompat.getColor(context, R.color.black)
        lineDataSet.setCircleColor(ContextCompat.getColor(context, R.color.blue))
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.setDrawValues(false)
        lineDataSet.lineWidth = 3f
        lineDataSet.circleRadius = 6f
        return lineDataSet
    }

    private fun getEntries(data: List<Int>): List<Entry> = data.mapIndexed { index, value -> Entry(index.toFloat(), value.toFloat()) }

}