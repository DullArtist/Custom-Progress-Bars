package com.app.charts.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.app.charts.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

@SuppressLint("ViewConstructor")
class CustomMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {

    private val tvContent: TextView = findViewById(R.id.tv_marker_view)

    // Override the refreshContent to update the marker view's text
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.let {
            // Display the Y-value with the unit "kg"
            tvContent.text = String.format("%.0f kg", e.y)
        }
        super.refreshContent(e, highlight)
    }

    // Position the marker relative to the selected point
    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat())
    }
}
