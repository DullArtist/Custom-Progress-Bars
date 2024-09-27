package com.app.charts.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.charts.CheckUp
import com.app.charts.CheckUpAdapter
import com.app.charts.R
import com.app.charts.databinding.ActivityCheckUpBinding
import com.app.charts.helpers.Utilities

class CheckUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckUpBinding
    private var list: List<CheckUp>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }



        list = getList()
        setAdapter(list!!)

        binding.btnSendCheckUp.setOnClickListener {
            var answered = 0
            for (i in 0 until list!!.size) {
                if (list!![i].isAnswered) {
                    answered ++
                }else {
                    Toast.makeText(this, "Please answer question no ${i+1}", Toast.LENGTH_SHORT).show()
                    break
                }
            }

            if (answered == list!!.size) {
                Toast.makeText(this, "Okay", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setAdapter(list: List<CheckUp>) {
        val adapter = CheckUpAdapter(this, list)
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