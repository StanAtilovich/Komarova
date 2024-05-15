package ru.stan.komarova.presentation.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.stan.komarova.R
import ru.stan.komarova.databinding.DialogBinding
import ru.stan.komarova.presentation.ui.fragments.HotelsFragment
import ru.stan.komarova.presentation.viewModel.MyViewModel

object DialogManager {
    fun showSaveDialog(context: Context, listener: Listener, viewModel: MyViewModel) {
        val builder = AlertDialog.Builder(context)
        val binding = DialogBinding.inflate(LayoutInflater.from(context), null, false)
        builder.setView(binding.root)
        builder.setCancelable(false)
        val dialog = builder.create()
        binding.apply {

            hardWay.setOnClickListener {
                listener.onClick()
                dialog.dismiss()
                openFragment(context, HotelsFragment.newInstance())
            }
            whereGo.setOnClickListener {
                viewModel.messageForFragment.value = "Куда угодно"
                listener.onClick()
                dialog.dismiss()
            }
            holidays.setOnClickListener {
                listener.onClick()
                dialog.dismiss()
                openFragment(context, HotelsFragment.newInstance())
            }
            hotTickets.setOnClickListener {
                listener.onClick()
                dialog.dismiss()
                openFragment(context, HotelsFragment.newInstance())
            }
            Istambul.setOnClickListener {
                viewModel.messageForFragment.value = "Стамбул"
                listener.onClick()
                dialog.dismiss()
            }
            Sochi.setOnClickListener {
                viewModel.messageForFragment.value = "Сочи"
                listener.onClick()
                dialog.dismiss()
            }
            Phuket.setOnClickListener {
                viewModel.messageForFragment.value = "Пхукет"
                listener.onClick()
                dialog.dismiss()
            }
        }
        dialog.show()


    }

    private fun openFragment(context: Context, fragment: Fragment){
        if (context is AppCompatActivity) {
            context.supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeHolder, fragment)
                .commit()
        }
    }

    interface Listener {
        fun onClick()
    }
}