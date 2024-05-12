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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import ru.stan.komarova.R
import ru.stan.komarova.databinding.FragmentSearchBinding
import ru.stan.komarova.viewModel.MyViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding = FragmentSearchBinding.inflate(inflater, container, false)


        val curentDay = getCurrentDate()
        binding.dateToday.text = curentDay

        binding.ShowTicketsButton.setOnClickListener {
            context?.let { it1 -> openFragment(it1, AllTicketsFragment.newInstance()) }
        }

        viewModel.editTextValueWhereFrom.observe(activity as LifecycleOwner, {
            binding.editWhereFrom.text = Editable.Factory.getInstance().newEditable(it)
        })

        viewModel.editTextValueWhere.observe(activity as LifecycleOwner, {
            binding.editWhere.text = Editable.Factory.getInstance().newEditable(it)
        })

        binding.imBack.setOnClickListener {
            viewModel.editTextValueWhere.value= binding.editWhere.text.toString()
            viewModel.editTextValueWhereFrom.value = binding.editWhereFrom.text.toString()
            back()
        }


        changeEditText()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    private fun openFragment(context: Context, fragment: Fragment) {
        if (context is AppCompatActivity) {
            context.supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeHolder, fragment)
                .commit()
        }
    }

    private fun back() = with(binding) {
        imBack.setOnClickListener {
            Toast.makeText(context, "назад", Toast.LENGTH_SHORT).show()
            context?.let { it1 -> openFragment(it1, TicketsFragment.newInstance()) }
        }
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


    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}