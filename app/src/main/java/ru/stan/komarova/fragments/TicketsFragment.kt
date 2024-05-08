package ru.stan.komarova.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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
    companion object {
        @JvmStatic
        fun newInstance() = TicketsFragment()
    }
}
