package ru.stan.komarova.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.stan.komarova.R

class HelperAdapter2(
    private val badge: ArrayList<String>,
    private val value: ArrayList<String>,
    private val date: ArrayList<String>,
    private val arivalDate: ArrayList<String>,
    private val departureAirport: ArrayList<String>,
    private val arivalAirport: ArrayList<String>,
    private val context: Context
) :
    RecyclerView.Adapter<HelperAdapter2.MyViewClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewClass {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.all_tikets_item, parent, false)
        return MyViewClass(view)
    }

    override fun onBindViewHolder(holder: MyViewClass, position: Int) {
        holder.bind(badge[position],value[position],  date[position], arivalDate[position],departureAirport[position], arivalAirport[position])

    }

    override fun getItemCount(): Int = departureAirport.size

    inner class MyViewClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val badgeTextView: TextView = itemView.findViewById(R.id.badge)
        private val titleTextView: TextView = itemView.findViewById(R.id.valueTextView)
        private val tvPriceFlightsTextView: TextView =
            itemView.findViewById(R.id.titleDividerNoCustom)

        private val date: TextView = itemView.findViewById(R.id.arrivalDate)
        private val arrivalDate: TextView = itemView.findViewById(R.id.town)
        private val arivalAirport: TextView = itemView.findViewById(R.id.arival_airport)


        fun bind(
            badge1: String,
            priceText: String,
            titleText: String,
            date: String,
            arrivalDate: String,
            arivalAirport: String
        ) {
            if (badge1.isNotEmpty()) {
                badgeTextView.visibility = View.VISIBLE
                badgeTextView.text = badge1
            } else {
                badgeTextView.visibility = View.GONE
            }
            tvPriceFlightsTextView.text = priceText + "  ₽"
            titleTextView.text = titleText
            this.date.text = date
            this.arrivalDate.text = arrivalDate
            this.arivalAirport.text = arivalAirport



            itemView.setOnClickListener {
                Toast.makeText(context, "Item Clicked", Toast.LENGTH_LONG).show()
            }
        }


    }
}
