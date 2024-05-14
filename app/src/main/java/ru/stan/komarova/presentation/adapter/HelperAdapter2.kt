package ru.stan.komarova.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.stan.komarova.R

class HelperAdapter2(private val title: ArrayList<String>, private val value: ArrayList<String>,private val town:ArrayList<String>,private val context: Context) :
    RecyclerView.Adapter<HelperAdapter2.MyViewClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_tikets_item, parent, false)
        return MyViewClass(view)
    }

    override fun onBindViewHolder(holder: MyViewClass, position: Int) {
        holder.bind(title[position], value[position],  town[position])
    }

    override fun getItemCount(): Int = title.size

    inner class MyViewClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.valueTextView)
        private val tvPriceFlightsTextView: TextView = itemView.findViewById(R.id.titleDividerNoCustom)
            //   private val badge: TextView = itemView.findViewById(R.id.badge)
        private val town: TextView = itemView.findViewById(R.id.town)



        fun bind(titleText: String, priceText: String,  town1: String) {
            titleTextView.text = titleText
            tvPriceFlightsTextView.text = priceText
           // badge.text = badge1
            town.text = town1


            itemView.setOnClickListener {
                Toast.makeText(context, "Item Clicked", Toast.LENGTH_LONG).show()
            }
        }
    }
}
