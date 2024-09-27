package com.app.charts.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.app.charts.databinding.ActivityGaugeChartBinding

class StepsChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGaugeChartBinding

    private var steps: Double = 0.0

    private var handler = Handler(Looper.getMainLooper())
    private var isLongPressed = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGaugeChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStepsChart(steps)

        binding.btnDecrease.setOnClickListener {
            if (steps > 0){
                setStepsChart(--steps)
            }
        }

        binding.btnIncrease.setOnClickListener {
            if (steps < 10000){
                setStepsChart(++steps)
            }
        }

        binding.btnDecrease.setOnLongClickListener {
            isLongPressed = true
            handler.post(decreaseRunnable)  // Start continuous decrease
            true
        }

        binding.btnIncrease.setOnLongClickListener {
            isLongPressed = true
            handler.post(increaseRunnable)  // Start continuous increase
            true
        }

        binding.btnDecrease.setOnTouchListener { _, event ->
            if (event.action == android.view.MotionEvent.ACTION_UP || event.action == android.view.MotionEvent.ACTION_CANCEL) {
                isLongPressed = false
            }
            false
        }

        binding.btnIncrease.setOnTouchListener { _, event ->
            if (event.action == android.view.MotionEvent.ACTION_UP || event.action == android.view.MotionEvent.ACTION_CANCEL) {
                isLongPressed = false
            }
            false
        }


    }

    private val increaseRunnable = object : Runnable {
        override fun run() {
            if (isLongPressed && steps < 10000.0) {
                steps += 100
                setStepsChart(steps)
                handler.postDelayed(this, 50)  // Continue after 50ms
            }
        }
    }

    private val decreaseRunnable = object : Runnable {
        override fun run() {
            if (isLongPressed && steps > 0.0) {
                if (steps >= 100) {
                    steps -= 100
                    setStepsChart(steps)
                } else {
                    steps = 0.0
                    setStepsChart(steps)
                }
                handler.postDelayed(this, 50)  // Continue after 50ms
            }
        }
    }

    private fun setStepsChart(steps: Double) {

        val totalSteps = steps
        val goalSteps = 10000

        // Set the max steps and current progress
        binding.progressBar.max = goalSteps
        binding.progressBar.progress = totalSteps.toInt()

        // Update the text to show current steps
        binding.tvSteps.text = "$totalSteps steps"
    }
}