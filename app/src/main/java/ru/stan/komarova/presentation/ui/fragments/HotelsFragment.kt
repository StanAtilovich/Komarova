package ru.stan.komarova.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.stan.komarova.databinding.FragmentHotelsBinding
import ru.stan.komarova.presentation.utils.openFragment

class HotelsFragment : Fragment() {
    private lateinit var binding: FragmentHotelsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotelsBinding.inflate(inflater, container, false)

        val backButton: Button = binding.button3
        backButton.setOnClickListener {
            (requireActivity() as AppCompatActivity).openFragment(TicketsFragment.newInstance())
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HotelsFragment()
    }
}

