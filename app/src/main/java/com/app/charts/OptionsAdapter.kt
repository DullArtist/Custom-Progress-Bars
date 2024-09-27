package com.app.charts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.charts.databinding.ItemCheckUpBinding
import com.app.charts.databinding.ItemOptionBinding

class OptionsAdapter(
    private val context: Context,
    private val options: List<Option>,
    private val type: String,
    private val callback: () -> Unit
): RecyclerView.Adapter<OptionsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemOptionBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemOptionBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvOption.text = options[position].option

        when (type) {
            "checkbox" -> {
                holder.binding.ivSelection.setImageResource(R.drawable.ic_check_box_unselected)
                holder.binding.root.setOnClickListener {
                    for (i in options.indices) {
                        options[i].selected = !options[i].selected
                        notifyItemChanged(i)
                    }

                    for (i in options.indices) {
                        if (options[i].selected) {
                            callback.invoke()
                            break
                        }
                    }

                }
            }
            "radio" -> {
                holder.binding.ivSelection.setImageResource(R.drawable.ic_radio_unselected)
                holder.binding.root.setOnClickListener {
                    for (i in options.indices) {
                        val isSelected = i == position
                        options[i].selected = isSelected
                        notifyItemChanged(i)
                    }
                    callback.invoke()
                }
            }
        }




    }

    override fun getItemCount(): Int = options.size



}