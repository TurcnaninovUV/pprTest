package com.pprtest.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pprtest.R
import com.pprtest.databinding.CardNumberBinding
import com.pprtest.dto.NumberItem

class NumberAdapter : ListAdapter<NumberItem, NumberViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val binding = CardNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NumberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val number = getItem(position)
        holder.bind(number)
    }
}


class NumberViewHolder(
    private val binding: CardNumberBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(numberItem: NumberItem) {
        binding.apply {
            numberText.text = numberItem.number.toString()
            if (numberItem.whiteColorNumber) numberText.setBackgroundResource(R.color.white)
            else numberText.setBackgroundResource(R.color.colorWhiteGrey)

        }
    }

}

object DiffCallback : DiffUtil.ItemCallback<NumberItem>() {
    override fun areItemsTheSame(oldItem: NumberItem, newItem: NumberItem): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: NumberItem, newItem: NumberItem): Boolean {
        return oldItem == newItem
    }
}


