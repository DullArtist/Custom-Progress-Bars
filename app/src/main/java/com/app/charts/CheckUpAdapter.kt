package com.app.charts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.charts.databinding.ItemCheckUpBinding

class CheckUpAdapter(
    private val context: Context,
    private val checkUps: List<CheckUp>
): RecyclerView.Adapter<CheckUpAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCheckUpBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCheckUpBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = checkUps[position]
        val binding = holder.binding

        model.type?.let {
            when (it) {
                "checkbox","radio" -> {
                    binding.rvOptions.visibility = View.VISIBLE
                    val optionsAdapter = OptionsAdapter(context,getOptionList(model.options!!),it) {

                    }
                    binding.rvOptions.adapter = optionsAdapter
                    binding.rvOptions.layoutManager = LinearLayoutManager(context)
                }
                "answer" -> {
                    binding.llAnswer.visibility = View.VISIBLE
                }
                "range" -> {
                    binding.llRange.visibility = View.VISIBLE
                }
            }

        }


    }

    private fun getOptionList(options: List<String>): ArrayList<Option> {
        val optionList = ArrayList<Option>()
        for (element in options) {
            optionList.add(Option(element))
        }
        return optionList
    }

    override fun getItemCount(): Int = checkUps.size



}