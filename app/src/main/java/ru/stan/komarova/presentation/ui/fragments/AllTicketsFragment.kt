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

class AllTicketsFragment : Fragment() {
    private lateinit var binding: FragmentAllTicketsBinding
    private lateinit var viewModel: MyViewModel

    //new
    private lateinit var recyclerView: RecyclerView
    private val titleList = ArrayList<String>()
    private val timeList = ArrayList<String>()
    private val valueList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding = FragmentAllTicketsBinding.inflate(inflater, container, false)
        cityShow()
            //new
        recyclerView = binding.recyclerViewSecond
        recyclerView.layoutManager = LinearLayoutManager(context)
        loadUserDataFromJson("users.json")
        recyclerView.adapter = context?.let { HelperAdapter2(titleList, timeList, valueList, it) }


        date()
        return binding.root

    }

    //new
    private fun loadUserDataFromJson(fileName: String) {
        try {
            val inputStream = context?.assets?.open(fileName)
            val size = inputStream?.available()
            val buffer = ByteArray(size!!)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            val jsonArray = JSONObject(jsonString).getJSONArray("tickets_offers")
            for (i in 0 until jsonArray.length()) {
                val userData = jsonArray.getJSONObject(i)
                titleList.add(userData.getString("title"))

                val timeRangeArray = userData.getJSONArray("time_range")
                val timeRangeStringBuilder = StringBuilder()
                for (j in 0 until timeRangeArray.length()) {
                    timeRangeStringBuilder.append(timeRangeArray.getString(j)).append(", ")
                }
                val timeRangeString = timeRangeStringBuilder.toString().trimEnd(',', ' ')
                timeList.add(timeRangeString)

                val priceObject = userData.getJSONObject("price")
                valueList.add(priceObject.getInt("value").toString())
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    //получил
    private fun date(){
        var date = ""
        var people = ", 1 пассажир"
        var fullDatePeople = ""
        viewModel.dateToday.observe(viewLifecycleOwner) {
            date= it
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