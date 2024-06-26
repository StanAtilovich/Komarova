package ru.stan.komarova.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import ru.stan.komarova.R
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HelperAdapter2(
    private val badge: ArrayList<String>,
    private val value: ArrayList<String>,
    private val date: ArrayList<String>,
    private val arivalDate: ArrayList<String>,
    private val departureAirport: ArrayList<String>,
    private val arivalAirport: ArrayList<String>,
    private val hasTransfer: ArrayList<String>,
    private val context: Context
) :
    RecyclerView.Adapter<HelperAdapter2.MyViewClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewClass {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.all_tikets_item, parent, false)
        return MyViewClass(view)
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: MyViewClass, position: Int) {
        holder.bind(
            badge[position],
            value[position],
            date[position],
            arivalDate[position],
            departureAirport[position],
            arivalAirport[position],
            hasTransfer[position]
        )

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
        private val timeDiference1: TextView = itemView.findViewById(R.id.allfliteTime)
        private val hasTransfer: TextView = itemView.findViewById(R.id.hasTransfer)


        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(
            badge1: String,
            priceText: String,
            titleText: String,
            dateTimeString: String,
            arrivalDate: String,
            arivalAirport: String,
            hasTransfer: String
        ) {
            if (badge1.isNotEmpty()) {
                badgeTextView.visibility = View.VISIBLE
                badgeTextView.text = badge1
            } else {
                badgeTextView.visibility = View.GONE
            }

            tvPriceFlightsTextView.text = priceText + " ₽"

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val dateTime = LocalDateTime.parse(dateTimeString, formatter)

            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val timeString = dateTime.format(timeFormatter)

            val formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val dateTime1 = LocalDateTime.parse(titleText, formatter1)

            val timeFormatter1 = DateTimeFormatter.ofPattern("HH:mm")
            val timeString1 = dateTime1.format(timeFormatter1)


            titleTextView.text = timeString1 + " - "

            this.date.text = timeString

            this.arrivalDate.text = arrivalDate
            this.arivalAirport.text = arivalAirport

            val timeDiferance = Duration.between(dateTime1, dateTime).seconds
            val hours = timeDiferance / 3600
            val minutes = (timeDiferance % 3600) / 60
            val timeDifferenceHoursMinutes = String.format("%d:%02d", hours, minutes)
            timeDiference1.text = timeDifferenceHoursMinutes + "ч в пути"


            val hasTransferBoolean = hasTransfer.toBoolean()
            this.hasTransfer.text = if (hasTransferBoolean) {
                "с пересадкой"
            } else {
                "без пересадок"
            }

            itemView.setOnClickListener {
                Toast.makeText(context, "Item Clicked", Toast.LENGTH_LONG).show()
            }
        }

    }

}



