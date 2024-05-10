package ru.stan.komarova.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import ru.stan.komarova.databinding.DialogBinding
import ru.stan.komarova.viewModel.MyViewModel

object DialogManager {
    fun showSaveDialog(context: Context, listener: Listener, viewModel: MyViewModel) {
        val builder = AlertDialog.Builder(context)
        val binding = DialogBinding.inflate(LayoutInflater.from(context), null, false)
        builder.setView(binding.root)
        builder.setCancelable(false) // Запретить закрытие диалога при нажатии вне его содержимого
        val dialog = builder.create()
        binding.apply {

            hardWay.setOnClickListener {
                listener.onClick()
                dialog.dismiss()
            }
            whereGo.setOnClickListener {
                viewModel.messageForFragment.value = "Куда угодно"
                listener.onClick()
                dialog.dismiss()
            }
            holidays.setOnClickListener {
                listener.onClick()
                dialog.dismiss()
            }
            hotTickets.setOnClickListener {
                listener.onClick()
                dialog.dismiss()
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


    interface Listener {
        fun onClick()
    }
}