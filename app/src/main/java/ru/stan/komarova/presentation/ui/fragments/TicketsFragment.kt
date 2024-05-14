package ru.stan.komarova.presentation.ui.fragments

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
import org.json.JSONException
import org.json.JSONObject
import ru.stan.komarova.R
import ru.stan.komarova.databinding.FragmentTicketsBinding
import ru.stan.komarova.presentation.adapter.HelperAdapter3
import ru.stan.komarova.presentation.adapter.TicketsRcAdapter
import ru.stan.komarova.presentation.utils.DialogManager
import ru.stan.komarova.presentation.viewModel.MyViewModel
import java.io.IOException

class TicketsFragment : Fragment() {
    private lateinit var binding: FragmentTicketsBinding
    private lateinit var adapter: TicketsRcAdapter
    private lateinit var viewModel: MyViewModel

    private val titleList = ArrayList<String>()
    private val timeRangeList = ArrayList<String>()

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


        val recyclerView = binding.rcView
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = HelperAdapter3(titleList, timeRangeList, requireContext())

        loadUserDataFromJson("users3.json")
        return binding.root
    }

    private fun loadUserDataFromJson(fileName: String) {
        try {
            val jsonString = context?.assets?.open(fileName)?.bufferedReader().use { it?.readText() }
            val jsonArray = JSONObject(jsonString).getJSONArray("offers")

            for (i in 0 until jsonArray.length()) {
                val offer = jsonArray.getJSONObject(i)
                titleList.add(offer.getString("title"))
                val town = offer.getString("town")
                val priceObject = offer.getJSONObject("price")
                val priceValue = priceObject.getInt("value")
                timeRangeList.add("$town - $priceValue")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
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