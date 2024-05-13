package ru.stan.komarova.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.stan.komarova.R
import ru.stan.komarova.adapter.TicketsRcAdapter
import ru.stan.komarova.databinding.FragmentTicketsBinding
import ru.stan.komarova.utils.DialogManager
import ru.stan.komarova.viewModel.MyViewModel

class TicketsFragment : Fragment() {
    private lateinit var binding: FragmentTicketsBinding
    private lateinit var adapter: TicketsRcAdapter
    private lateinit var viewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        binding = FragmentTicketsBinding.inflate(inflater, container, false)
        adapter = TicketsRcAdapter()
        binding.rcView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rcView.adapter = adapter
        val inputFilter = viewModel.getInputFilter()
        binding.editWhere1.filters = arrayOf(inputFilter)
        binding.editWhereFrom1.filters = arrayOf(inputFilter)
        setupListeners()
        viewModel.offerLiveData.observe(viewLifecycleOwner, Observer { offers ->
            adapter.submitList(offers)
        })
        viewModel.fetchOffersFromApi()
        return binding.root
    }
    private fun openFragment(context: Context?, fragment: Fragment) {
        if (context is AppCompatActivity) {
            context.supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeHolder, fragment)
                .commit()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.messageForFragment.observe(viewLifecycleOwner) { message ->
            binding.editWhere1.setText(message)
        }
        viewModel.editTextValueWhereFrom.observe(viewLifecycleOwner) { value ->
            binding.editWhereFrom1.setText(value)
        }
        binding.searchTickets.setOnClickListener {
            openFragment(context, SearchFragment.newInstance())
            viewModel.editTextValueWhereFrom.value = binding.editWhereFrom1.text.toString()
            viewModel.editTextValueWhere.value = binding.editWhere1.text.toString()
    }
        viewModel.editTextValueWhere.observe(viewLifecycleOwner) { value ->
            binding.editWhere1.setText(value)
        }
    }
    private fun setupListeners() {
        with(binding) {
            viewModel.getInputFilter()
            clearText.setOnClickListener {
                editWhere1.text.clear()
                viewModel.isDialogOpen = false
            }
            clearText1.setOnClickListener {
                editWhereFrom1.text.clear()
                viewModel.isDialogOpen = false
            }
            setupTextListener(editWhere1, clearText)
            setupTextListener(editWhereFrom1, clearText1)
            addPlaneImageTextWatcher(editWhere1, imageView2)
            addPlaneImageTextWatcher(editWhereFrom1, plane)
        }
        binding.editWhere1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (viewModel.textChangedByUser && !viewModel.isDialogOpen) {
                    openDialog()
                    viewModel.isDialogOpen = true
                }
            }
            override fun afterTextChanged(s: Editable?) {
                viewModel.textChangedByUser = s.toString().isNotEmpty()
            }
        })
    }
    private fun openDialog() {
        if (!viewModel.isDialogOpen) {
            viewModel.isDialogOpen = false
            val context = context ?: return
            viewModel.let { myViewModel ->
                DialogManager.showSaveDialog(context, object : DialogManager.Listener {
                    override fun onClick() {
                        Toast.makeText(context, "пошло", Toast.LENGTH_SHORT).show()
                        viewModel.isDialogOpen = true
                    }
                }, myViewModel)
            }
        }
    }
    private fun setupTextListener(editText: EditText, clearImageView: ImageView) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                clearImageView.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
        })
    }
    private fun addPlaneImageTextWatcher(editText: EditText, imageView: ImageView) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                imageView.visibility = if (s.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    companion object {
        @JvmStatic
        fun newInstance() = TicketsFragment()
    }
}