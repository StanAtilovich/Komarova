package ru.stan.komarova.presentation.ui.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
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
import ru.stan.komarova.databinding.FragmentSearchBinding
import ru.stan.komarova.presentation.adapter.HelperAdapter
import ru.stan.komarova.presentation.viewModel.MyViewModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerView: RecyclerView
    private val titleList = ArrayList<String>()
    private val timeList = ArrayList<String>()
    private val valueList = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val currentDate = getCurrentDate()
        binding.dateToday.text = currentDate
        binding.ShowTicketsButton.setOnClickListener {
            context?.let { openFragment(it, AllTicketsFragment.newInstance()) }
            viewModel.editTextValueWhere.value = binding.editWhere.text.toString()
            viewModel.editTextValueWhereFrom.value = binding.editWhereFrom.text.toString()

            viewModel.dateToday.value = binding.dateToday.text.toString()
        }
        viewModel.editTextValueWhereFrom.observe(viewLifecycleOwner) {
            binding.editWhereFrom.text = it.toEditable()
        }
        viewModel.editTextValueWhere.observe(viewLifecycleOwner) {
            binding.editWhere.text = it.toEditable()
        }
        binding.imBack.setOnClickListener {
            viewModel.editTextValueWhere.value = binding.editWhere.text.toString()
            viewModel.editTextValueWhereFrom.value = binding.editWhereFrom.text.toString()
            back()
        }

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        loadUserDataFromJson("users.json")
        recyclerView.adapter = context?.let { HelperAdapter(titleList, timeList, valueList, it) }
        setupViews()
        return binding.root
    }

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



    private fun setupViews() {
        with(binding) {
            change.setOnClickListener {
                val textWhereFrom = editWhereFrom.text
                val textWhere = editWhere.text

                editWhereFrom.text = textWhere.toEditable()
                editWhere.text = textWhereFrom.toEditable()
            }

            addDate.setOnClickListener {
                showDatePickerDialog()
            }
        }
    }

    private fun openFragment(context: Context, fragment: Fragment) {
        if (context is AppCompatActivity) {
            context.supportFragmentManager.beginTransaction()
                .replace(R.id.placeHolder, fragment)
                .commit()
        }
    }

    private fun back() {
        Toast.makeText(context, "назад", Toast.LENGTH_SHORT).show()
        context?.let { openFragment(it, TicketsFragment.newInstance()) }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerDialog(requireContext())
        datePicker.setOnDateSetListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            binding.dateView.text = selectedDate
        }
        datePicker.show()
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM , EE", Locale.getDefault())
        return sdf.format(Date())
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    private fun CharSequence.toEditable(): Editable {
        return Editable.Factory.getInstance().newEditable(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
