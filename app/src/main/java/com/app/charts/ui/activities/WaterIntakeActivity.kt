package com.app.charts.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.app.charts.databinding.ActivityWaterIntakeBinding

class WaterIntakeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWaterIntakeBinding
    private var hydration = 0f //should be from 0 till 1 where 0.1 represent 1 liter

    private var handler = Handler(Looper.getMainLooper())
    private var isLongPressed = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaterIntakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateWaterLevel(hydration)

        binding.btnIncrease.setOnClickListener {
            if (hydration < 1.0f) {
                hydration += 0.1f
                updateWaterLevel(hydration)
            }

        }

        binding.btnDecrease.setOnClickListener {
            if (hydration > 0) {
                hydration -= 0.1f
                updateWaterLevel(hydration)
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
            if (isLongPressed && hydration < 1.0) {
                hydration += 0.1f
                updateWaterLevel(hydration)
                handler.postDelayed(this, 50)  // Continue after 50ms
            }
        }
    }

    private val decreaseRunnable = object : Runnable {
        override fun run() {
            if (isLongPressed && hydration > 0.0) {
                if (hydration >= 0.1) {
                    hydration -= 0.1f
                    updateWaterLevel(hydration)
                } else {
                    hydration = 0.0f
                    updateWaterLevel(hydration)
                }
                handler.postDelayed(this, 50)  // Continue after 50ms
            }
        }
    }

    private fun updateWaterLevel(ml: Float) {
        binding.waterView.setWaterLevel(ml)
        val waterIntake = ml * 1000
        binding.tvWater.text = String.format("%dml", waterIntake.toInt())
    }

}