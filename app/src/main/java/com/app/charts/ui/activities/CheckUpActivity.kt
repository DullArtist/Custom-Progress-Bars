package com.app.charts.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.charts.CheckUp
import com.app.charts.CheckUpAdapter
import com.app.charts.R
import com.app.charts.databinding.ActivityCheckUpBinding

class CheckUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        setAdapter()

    }

    private fun setAdapter() {
        val adapter = CheckUpAdapter(this, getList())
        binding.rvCheckUp.adapter = adapter
        binding.rvCheckUp.layoutManager = LinearLayoutManager(this)
    }

    private fun getList(): List<CheckUp> {
        val list = ArrayList<CheckUp>()
        list.add(CheckUp("","checkbox",getOptionList()))
        list.add(CheckUp("","answer",null))
        list.add(CheckUp("","radio",getOptionList()))
        list.add(CheckUp("","range",null))
        list.add(CheckUp("","checkbox",getOptionList()))
        list.add(CheckUp("","answer",null))
        list.add(CheckUp("","radio",getOptionList()))
        list.add(CheckUp("","range",null))

        return list
    }

    private fun getOptionList(): List<String> {
        val list = ArrayList<String>()
        list.add("Option 1")
        list.add("Option 2")
        list.add("Option 3")
        list.add("Option 4")

        return list
    }
}