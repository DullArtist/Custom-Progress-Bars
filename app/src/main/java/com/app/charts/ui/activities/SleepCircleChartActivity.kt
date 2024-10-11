package com.app.charts.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.charts.R
import com.app.charts.databinding.ActivitySleepCircleChartBinding

class SleepCircleChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepCircleChartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepCircleChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateSleepDuration(9,30,1,30)

    }

    private fun updateSleepDuration(bedHour: Int, bedMinute: Int, wakeHour: Int, wakeMinute: Int) {
        val bedTimeInMinutes = bedHour * 60 + bedMinute
        val wakeTimeInMinutes = wakeHour * 60 + wakeMinute

        val sleepDurationInMinutes = if (wakeTimeInMinutes == bedTimeInMinutes) { 0 }
        else if (wakeTimeInMinutes > bedTimeInMinutes) {
            wakeTimeInMinutes - bedTimeInMinutes
        } else {
            (24 * 60 - bedTimeInMinutes) + wakeTimeInMinutes  // Handles overnight sleep
        }

        // Calculate hours and minutes for display
        val hours = sleepDurationInMinutes / 60
        val minutes = sleepDurationInMinutes % 60

        binding.tvSleepProgress.text = "${hours}h ${minutes}m"

        binding.sleepChart.setSleepProgress(sleepDurationInMinutes.toFloat())
    }

}