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

        val currentDate = getCurrentDate()
        binding.dateToday.text = currentDate

        binding.ShowTicketsButton.setOnClickListener {
            context?.let { openFragment(it, AllTicketsFragment.newInstance()) }
            viewModel.editTextValueWhere.value = binding.editWhere.text.toString()
            viewModel.editTextValueWhereFrom.value = binding.editWhereFrom.text.toString()
                //отправил
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
        setupViews()
        return binding.root
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
}
