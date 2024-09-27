package com.app.charts.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.charts.R
import com.app.charts.databinding.ActivityWeightProgressBinding

class WeightProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeightProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeightProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setWeightProgress()


    }

    private fun setWeightProgress() {
        val currentWeight = 55f
        val goalWeight = 62f
        val startWeight = 50f
        val weightProgress = ((currentWeight - startWeight) / (goalWeight - startWeight)) * 100
        binding.weightProgress.setWeightProgress(weightProgress)

        val gainedWeight = currentWeight - startWeight
        val progress = goalWeight - startWeight
        binding.tvWeight.text = "+${gainedWeight.toInt()} Kg"
        binding.tvFrom.text = "From +${progress.toInt()} Kg"

    }
}