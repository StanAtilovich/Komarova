package ru.stan.komarova.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.stan.komarova.R

class HelperAdapter3(private val title: ArrayList<String>, private val value: ArrayList<String>, private val context: Context) :
    RecyclerView.Adapter<HelperAdapter3.MyViewClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lenta_item, parent, false)
        return MyViewClass(view)
    }

    override fun onBindViewHolder(holder: MyViewClass, position: Int) {
        holder.bind(title[position], value[position])
    }

    override fun getItemCount(): Int = title.size

    inner class MyViewClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvName)
        private val tvPriceFlightsTextView: TextView = itemView.findViewById(R.id.tvTown)
        private val myImageView: ImageView = itemView.findViewById(R.id.myImageeView)

        fun bind(titleText: String, priceText: String) {
            titleTextView.text = titleText
            tvPriceFlightsTextView.text = priceText
            when (adapterPosition) {
                0 -> myImageView.setImageResource(R.drawable.img1)
                1 -> myImageView.setImageResource(R.drawable.img2)
                2 -> myImageView.setImageResource(R.drawable.img3)
                else -> myImageView.setImageResource(R.drawable.default_image)
            }

            itemView.setOnClickListener {
                Toast.makeText(context, "Item Clicked", Toast.LENGTH_LONG).show()
            }
        }
    }
}
