package com.app.charts.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.charts.databinding.ActivitySleepChartBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class SleepChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepChartBinding
    private var timePicker: MaterialTimePicker? = null

    private var bedHour: Int? = null
    private var wakeHour: Int? = null
    private var bedMinute: Int? = null
    private var wakeMinute: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvBedTime.setOnClickListener { pickTime("Bed Time") }
        binding.tvWakeTime.setOnClickListener { pickTime("Wake Time") }


    }

    private fun pickTime(title: String) {
        timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(30)
                .setTitleText(title)
                .build()

        timePicker!!.show(supportFragmentManager,"")

        timePicker!!.addOnPositiveButtonClickListener {
            val hour: Int = timePicker!!.hour
            val minute: Int = timePicker!!.minute

            val amPm = if (hour < 12) "AM" else "PM"
            val displayHour = if (hour == 0) 12 else if (hour > 12) hour - 12 else hour
            val time = String.format("%02d:%02d %s", displayHour, minute, amPm)

            if (title == "Bed Time") {
                binding.tvBedTime.text = time
                bedHour = hour
                bedMinute = minute
            } else {
                binding.tvWakeTime.text = time
                wakeHour = hour
                wakeMinute = minute
            }

            // Check if both bed and wake times are set before updating the progress
            if (bedHour != null && wakeHour != null) {
                updateSleepDuration(bedHour!!, bedMinute!!, wakeHour!!, wakeMinute!!)
            }

        }



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