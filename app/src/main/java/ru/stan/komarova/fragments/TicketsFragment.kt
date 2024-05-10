package ru.stan.komarova.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.stan.komarova.adapter.TicketsRcAdapter
import ru.stan.komarova.databinding.FragmentTicketsBinding
import ru.stan.komarova.util.DialogManager
import ru.stan.komarova.viewModel.MyViewModel

@Suppress("UNREACHABLE_CODE")
class TicketsFragment : Fragment() {
    private lateinit var binding: FragmentTicketsBinding
    private lateinit var adapter: TicketsRcAdapter
    private lateinit var viewModel: MyViewModel
    private var isDialogOpen = false
    private var textChangedByUser = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        binding = FragmentTicketsBinding.inflate(inflater, container, false)

        adapter = TicketsRcAdapter()
        binding.rcView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rcView.adapter = adapter

        val inputFilter = viewModel.getInputFilter()
        binding.editWhere.filters = arrayOf(inputFilter)

        // setText() вместо addTextChangedListener() для избегания инициализации при старте
        binding.editWhereFrom.setText(viewModel.editTextValue)

        setupListeners()

        viewModel.offerLiveData.observe(viewLifecycleOwner, Observer { offers ->
            adapter.submitList(offers)
        })

        viewModel.fetchOffersFromApi()

        return binding.root
    }

    private fun setupListeners() {
        with(binding) {
            viewModel.getInputFilter()

            clearText.setOnClickListener {
                editWhere.text.clear()
            }
            clearText1.setOnClickListener {
                editWhereFrom.text.clear()
            }

            setupTextListener(editWhere, clearText)
            setupTextListener(editWhereFrom, clearText1)
            addPlaneImageTextWatcher(editWhere, imageView2)
            addPlaneImageTextWatcher(editWhereFrom, plane)
        }

        binding.editWhere.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (textChangedByUser && !isDialogOpen) {
                    openDialog()
                    isDialogOpen = true
                }
            }

            override fun afterTextChanged(s: Editable?) {
                textChangedByUser = s.toString().isNotEmpty()
            }
        })

//        binding.editWhereFrom.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (textChangedByUser && !isDialogOpen) {
//                    openDialog()
//                    isDialogOpen = true
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                textChangedByUser = s.toString().isNotEmpty()
//            }
//        })
    }

    private fun openDialog() {
        val context = context ?: return
        val myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        DialogManager.showSaveDialog(context, object : DialogManager.Listener {
            override fun onClick() {
                Toast.makeText(context, "пошло", Toast.LENGTH_SHORT).show()
                isDialogOpen = false
            }
        }, myViewModel)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.messageForFragment.observe(viewLifecycleOwner) { message ->
            binding.editWhere.setText(message)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.editTextValue = binding.editWhereFrom.text.toString()
        super.onSaveInstanceState(outState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TicketsFragment()
    }
}