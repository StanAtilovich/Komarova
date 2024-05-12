package ru.stan.komarova.fragments

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

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeEditText()
        back()

    }

    private fun changeEditText(){
        binding.change.setOnClickListener {
            val textWhereFrom = binding.editWhereFrom.text
            val textWhere = binding.editWhere.text

            binding.editWhereFrom.text = Editable.Factory.getInstance().newEditable(textWhere)
            binding.editWhere.text = Editable.Factory.getInstance().newEditable(textWhereFrom)
        }
    }



    private fun back() = with(binding) {
        imBack.setOnClickListener {
            Toast.makeText(context, "назад", Toast.LENGTH_SHORT).show()
            context?.let { it1 -> openFragment(it1, TicketsFragment.newInstance()) }
        }
    }
    private fun openFragment(context: Context, fragment: Fragment){
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