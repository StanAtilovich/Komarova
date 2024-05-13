package ru.stan.komarova.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.stan.komarova.R
import ru.stan.komarova.databinding.FragmentAllTicketsBinding
import ru.stan.komarova.viewModel.MyViewModel

class AllTicketsFragment : Fragment() {
    private lateinit var binding: FragmentAllTicketsBinding
    private lateinit var viewModel: MyViewModel
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




        return binding.root

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