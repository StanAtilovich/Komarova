package ru.stan.komarova.fragments

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
import ru.stan.komarova.R
import ru.stan.komarova.databinding.FragmentSearchBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        val curentDay = getCurrentDate()
        binding.dateToday.text = curentDay

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeEditText()
        back()

    }

    private fun changeEditText() = with(binding) {
        change.setOnClickListener {
            val textWhereFrom = editWhereFrom.text
            val textWhere = editWhere.text

            editWhereFrom.text = Editable.Factory.getInstance().newEditable(textWhere)
            editWhere.text = Editable.Factory.getInstance().newEditable(textWhereFrom)
        }
        addDate.setOnClickListener {
            showDatePickerDialog()

        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerDialog(requireContext())
        datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            binding.dateView.text = selectedDate
        }
        datePicker.show()
    }


    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM , EE", Locale.getDefault())
        return sdf.format(Date())
    }


    private fun back() = with(binding) {
        imBack.setOnClickListener {
            Toast.makeText(context, "назад", Toast.LENGTH_SHORT).show()
            context?.let { it1 -> openFragment(it1, TicketsFragment.newInstance()) }
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
        fun newInstance() = SearchFragment()
    }
}