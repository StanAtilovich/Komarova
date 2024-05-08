package ru.stan.komarova.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.stan.komarova.Offer
import ru.stan.komarova.R
import ru.stan.komarova.databinding.LentaItemBinding

class TicketsRcAdapter : ListAdapter<Offer, TicketsRcAdapter.Holder>(Comparator()) {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LentaItemBinding.bind(view)

        fun bind(offer: Offer) = with(binding) {
            tvName.text = offer.title
            tvTown.text = offer.town
            val priceString = offer.price.toString()
            val price = priceString.replace(Regex("[^\\d]"), "").toInt()
            tvPrice.text = "от $price ₽"
        }
    }


    class Comparator : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lenta_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}