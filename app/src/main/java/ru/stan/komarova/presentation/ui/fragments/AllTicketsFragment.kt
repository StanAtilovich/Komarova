package ru.stan.komarova.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import ru.stan.komarova.R
import ru.stan.komarova.databinding.FragmentAllTicketsBinding
import ru.stan.komarova.presentation.adapter.HelperAdapter2
import ru.stan.komarova.presentation.viewModel.MyViewModel
import java.io.IOException

@Suppress("IMPLICIT_CAST_TO_ANY")
class AllTicketsFragment : Fragment() {
    private lateinit var binding: FragmentAllTicketsBinding
    private lateinit var viewModel: MyViewModel

    private lateinit var recyclerView: RecyclerView
    private val providerName = ArrayList<String>()
    private val badge = ArrayList<String>()
    private val value = ArrayList<String>()
    private val date = ArrayList<String>()
    private val departureAirport = ArrayList<String>()
    private val arivalAirport = ArrayList<String>()
    private val arrivalDate = ArrayList<String>()
    private val hasTransfer = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding = FragmentAllTicketsBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerViewSecond
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        loadUserDataFromJson("users2.json")
        recyclerView.adapter = HelperAdapter2(
            badge,
            value,
            date,
            arrivalDate,
            departureAirport,
            arivalAirport,
            hasTransfer,
            requireContext()
        )
        date()
        cityShow()
        return binding.root
    }

    private fun loadUserDataFromJson(fileName: String) {
        try {
            val jsonString =
                context?.assets?.open(fileName)?.bufferedReader().use { it?.readText() }
            val jsonArray = JSONObject(jsonString).getJSONArray("tickets")

            for (i in 0 until jsonArray.length()) {
                val ticket = jsonArray.getJSONObject(i)


                val providerNameList = ticket.getString("provider_name")
                val priceObject = ticket.getJSONObject("price")
                val priceValue = priceObject.getInt("value")
                value.add(priceValue.toString())

                val departureObject = ticket.getJSONObject("departure")
                val townValue = departureObject.getString("date")
                date.add(townValue)

                val hasTransferString = ticket.getString("has_transfer")
                val hasTransferValue = hasTransferString.toBoolean()
                hasTransfer.add(hasTransferValue.toString())

                val arrivalObject = ticket.getJSONObject("arrival")
                val arrivalValue = arrivalObject.getString("date")
                arrivalDate.add(arrivalValue)
                val arAirport = arrivalObject.getString("airport")
                arivalAirport.add(arAirport)

                val departureAirport2 = departureObject.getString("airport")
                departureAirport.add(departureAirport2)

                providerName.add(providerNameList)


                val badgeList =
                    if (ticket.has("badge") && !ticket.getString("badge").isNullOrEmpty()) {
                        ticket.getString("badge")
                    } else {
                        ""
                    }
                badge.add(badgeList)
            }


        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun date() {
        var date = ""
        var people = ", 1 пассажир"
        var fullDatePeople = ""
        viewModel.dateToday.observe(viewLifecycleOwner) {
            date = it
            fullDatePeople = date + people
            binding.data.text = fullDatePeople
        }
    }


    private fun cityShow() {
        var city = ""
        var city2 = ""
        var fullCity = ""

        viewModel.editTextValueWhere.observe(viewLifecycleOwner) {
            city = it
            fullCity = city + " " + city2
            binding.city.text = fullCity
        }

        viewModel.editTextValueWhereFrom.observe(viewLifecycleOwner) {
            city2 = it
            fullCity = city + " - " + city2
            binding.city.text = fullCity
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back()
    }

    private fun back() = with(binding) {
        imBack.setOnClickListener {
            Toast.makeText(context, "назад", Toast.LENGTH_SHORT).show()
            context?.let { it1 -> openFragment(it1, SearchFragment.newInstance()) }
        }
    }

    private fun openFragment(context: Context, fragment: Fragment) {
        if (context is AppCompatActivity) {
            context.supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeHolder, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AllTicketsFragment()
    }
}