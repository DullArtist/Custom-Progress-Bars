package com.app.charts.helpers

import android.content.Context
import java.time.LocalDate
import java.util.Calendar

object Utilities {

    fun getMonth(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH);
    }

    fun getDate(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DATE);
    }

    // Convert dp to pixels
    fun dpToPx(dp: Float, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }
}