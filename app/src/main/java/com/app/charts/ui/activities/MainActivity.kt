package com.app.charts.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.app.charts.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var context: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.root)

        init()

    }

    private fun init() {
        context = this;

        initClickListeners()
    }

    private fun initClickListeners() {
        binding.btnLineChart.setOnClickListener {
            val intent = Intent(context, LineChartActivity::class.java)
            startActivity(intent)
        }

        binding.btnBarChart.setOnClickListener {
            val intent = Intent(context, BarChartActivity::class.java)
            startActivity(intent)
        }

        binding.btnHydration.setOnClickListener {
            val intent = Intent(context, WaterIntakeActivity::class.java)
            startActivity(intent)
        }

        binding.btnSteps.setOnClickListener {
            val intent = Intent(context, StepsChartActivity::class.java)
            startActivity(intent)
        }

        binding.btnSleepChart.setOnClickListener {
            val intent = Intent(context, SleepChartActivity::class.java)
            startActivity(intent)
        }

        binding.btnWeight.setOnClickListener {
            val intent = Intent(context, WeightProgressActivity::class.java)
            startActivity(intent)
        }

        binding.btnCheckUp.setOnClickListener {
            val intent = Intent(context, CheckUpActivity::class.java)
            startActivity(intent)
        }
    }

}