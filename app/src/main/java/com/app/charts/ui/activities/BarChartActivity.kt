package com.app.charts.ui.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.charts.databinding.ActivityBarChartBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate.rgb


class BarChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBarChartBinding
    private lateinit var context: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    private fun init() {
        context = this;

        setBarChart()

    }


    private fun setBarChart() {

        // Define the data
        val calEntries = barCalEntries()
        val carbEntries = barCarbEntries()
        val proEntries = barProEntries()
        val fatEntries = barFatEntries()

        // Create datasets
        val calDataSet = BarDataSet(calEntries, "Calories").apply {
            color = rgb("#00A77E")
            valueTextSize = 10f
        }
        val carbDataSet = BarDataSet(carbEntries, "Carbs").apply {
            color = rgb("#105DFB")
            valueTextSize = 10f
        }
        val proDataSet = BarDataSet(proEntries, "Proteins").apply {
            color = rgb("#7F3BF3")
            valueTextSize = 10f
        }
        val fatDataSet = BarDataSet(fatEntries, "Fats").apply {
            color = rgb("#ECA20F")
            valueTextSize = 10f
        }

        // Group data
        val data = BarData(calDataSet, carbDataSet, proDataSet, fatDataSet)
        binding.barChart.data = data

        val indexValues = arrayOf("Mon, 1 Jun", "Tue, 2 Jun", "Wed, 3 Jun", "Thu, 4 Jun", "Fri, 5 Jun", "Sat, 6 Jun", "Sun, 7 Jun").toList()


        setYAxis()
        setXAxis(indexValues)
        setChartBehaviour()

        binding.barChart.invalidate() // Refresh the chart

    }

    private fun setYAxis() {
        val yAxis = binding.barChart.axisLeft
        yAxis.axisMinimum = 0f  // Set minimum value for Y-axis
        yAxis.axisMaximum = 100f  // Set maximum value for Y-axis

        // Set the interval (granularity) between Y-axis values This sets the Y-axis increments to 5 unit (you can adjust this)
        yAxis.granularity = 25f
        yAxis.setLabelCount(5, true)

        yAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return "${value.toInt()}%"
            }
        }
    }

    private fun setXAxis(indexValues: List<String>) {

        val xAxis = binding.barChart.xAxis
        // Customize the chart
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(indexValues)
        binding.barChart.axisRight.isEnabled = false

        xAxis.setDrawGridLines(false)

        xAxis.granularity = 1f

        xAxis.setLabelCount(6);
        xAxis.setAxisMinimum(0f)
        xAxis.setAxisMaximum(7f)
        xAxis.setCenterAxisLabels(true);

    }

    private fun setChartBehaviour() {
        binding.barChart.legend.isEnabled = false
        binding.barChart.setVisibleXRangeMaximum(3f)
        binding.barChart.description.isEnabled = false
        binding.barChart.isDoubleTapToZoomEnabled = false
        binding.barChart.setPinchZoom(false)
        binding.barChart.setScaleEnabled(false)

        val barSpace = 0.02f
        val groupSpace = 1 - (barSpace + 0.15f) * 4


        binding.barChart.barData.barWidth = 0.15f
        binding.barChart.groupBars(0f, groupSpace, barSpace) // perform the "explicit" grouping


        binding.barChart.barData.setDrawValues(false)
    }


    private fun barFatEntries(): ArrayList<BarEntry> {
        val fatEntries = arrayListOf(
            BarEntry(0f, 70f),  // Mon, 1 Jun
            BarEntry(1f, 80f),  // Tue, 2 Jun
            BarEntry(2f, 60f),  // Wed, 3 Jun
            BarEntry(3f, 20f),  // Thu, 4 Jun
            BarEntry(4f, 50f),  // Fri, 5 Jun
            BarEntry(5f, 12f),  // Sat, 6 Jun
            BarEntry(6f, 82f)   // Sun, 7 Jun
        )
        return fatEntries
    }

    private fun barProEntries(): ArrayList<BarEntry> {
        val proEntries = arrayListOf(
            BarEntry(0f, 90f),  // Mon, 1 Jun
            BarEntry(1f, 70f),  // Tue, 2 Jun
            BarEntry(2f, 50f),  // Wed, 3 Jun
            BarEntry(3f, 20f),  // Thu, 4 Jun
            BarEntry(4f, 70f),  // Fri, 5 Jun
            BarEntry(5f, 42f),  // Sat, 6 Jun
            BarEntry(6f, 95f)  // Sun, 7 Jun
        )
        return proEntries
    }

    private fun barCarbEntries(): ArrayList<BarEntry> {
        val carbEntries = arrayListOf(
            BarEntry(0f, 90f),  // Mon, 1 Jun
            BarEntry(1f, 70f),  // Tue, 2 Jun
            BarEntry(2f, 10f),  // Wed, 3 Jun
            BarEntry(3f, 20f),  // Thu, 4 Jun
            BarEntry(4f, 83f),  // Fri, 5 Jun
            BarEntry(5f, 73f),  // Sat, 6 Jun
            BarEntry(6f, 45f)   // Sun, 7 Jun
        )
        return carbEntries
    }

    private fun barCalEntries(): ArrayList<BarEntry> {
        val calEntries = arrayListOf(
            BarEntry(0f, 92f), // Mon, 1 Jun
            BarEntry(1f, 80f),  // Tue, 2 Jun
            BarEntry(2f, 20f),  // Wed, 3 Jun
            BarEntry(3f, 20f),  // Thu, 4 Jun
            BarEntry(4f, 53f),  // Fri, 5 Jun
            BarEntry(5f, 30f),  // Sat, 6 Jun
            BarEntry(6f, 72f)   // Sun, 7 Jun
        )
        return calEntries
    }


}