package ru.stan.komarova.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.stan.komarova.TicketsApi
import ru.stan.komarova.adapter.TicketsRcAdapter
import ru.stan.komarova.databinding.FragmentTicketsBinding

class TicketsFragment : Fragment() {
    private lateinit var binding: FragmentTicketsBinding
    private lateinit var adapter: TicketsRcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketsBinding.inflate(inflater, container, false)

        adapter = TicketsRcAdapter()
        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rcView.layoutManager = horizontalLayoutManager

        binding.rcView.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val roomApi = retrofit.create(TicketsApi::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            val room = roomApi.getTicketsById()
            binding.apply {
                adapter.submitList(room.offers)
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            val regex = Regex("[А-Яа-я ]+")
            if (!regex.matches(source)) {
                return@InputFilter ""
            }
            null
        }
        editWhereFrom.filters = arrayOf(filter)
        editWhere.filters = arrayOf(filter)

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

    private fun setupTextListener(editText: EditText, clearImageView: ImageView){
        editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                clearImageView.visibility = if (p0.isNullOrEmpty() )View.GONE else View.VISIBLE
            }
        })
    }

    fun addPlaneImageTextWatcher(editText: EditText, imageView: ImageView) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Не используется
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Показываем ImageView с изображением самолета при изменении текста в EditText
                imageView.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                // Не используется
            }
        })
    }



    companion object {
        @JvmStatic
        fun newInstance() = TicketsFragment()
    }
}
