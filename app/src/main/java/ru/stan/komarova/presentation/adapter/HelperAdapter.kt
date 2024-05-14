package ru.stan.komarova.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.stan.komarova.R

class HelperAdapter(private val title: ArrayList<String>, private val time: ArrayList<String>, private val value: ArrayList<String>,private val context: Context) :
    RecyclerView.Adapter<HelperAdapter.MyViewClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.right_flights_item, parent, false)
        return MyViewClass(view)
    }

    override fun onBindViewHolder(holder: MyViewClass, position: Int) {
        holder.bind(title[position], time[position], value[position])
//
//        holder.tvPriceFlightsTextView.text = it.context.resources.getString(R.string.right_flights_item)
//
//
//        holder.tvPriceFlightsTextView.ellipsize = TextUtils.TruncateAt.END
//        holder.tvPriceFlightsTextView.maxLines = 1
    }

    override fun getItemCount(): Int = title.size

    inner class MyViewClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
        val tvPriceFlightsTextView: TextView = itemView.findViewById(R.id.tvTime)
        private val tvTimeTextView: TextView = itemView.findViewById(R.id.tvPriceFlights)


        fun bind(titleText: String, priceText: String, timeText: String) {
            titleTextView.text = titleText
            tvTimeTextView.text = timeText + "  ₽"

            val maxLength = 40 // Максимальная длина текста
            val trimmedPriceText = if (priceText.length > maxLength) {
                priceText.substring(0, maxLength) + "..."
            } else {
                priceText
            }

            tvPriceFlightsTextView.text = trimmedPriceText



            itemView.setOnClickListener {
                Toast.makeText(context, "Item Clicked", Toast.LENGTH_LONG).show()
            }
        }
    }
}
