package ru.stan.komarova.presentation.viewModel

import android.text.InputFilter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.stan.komarova.domain.model.Offer

class MyViewModel : ViewModel() {
    var textChangedByUser = false
    val offerLiveData = MutableLiveData<List<Offer>>()
    val editTextValueWhereFrom = MutableLiveData<String>()
    val editTextValueWhere = MutableLiveData<String>()
    val dateToday = MutableLiveData<String>()


    var isDialogOpen = false

    val messageForFragment: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }




    fun getInputFilter() = InputFilter { source, start, end, dest, dstart, dend ->
        val regex = Regex("[А-Яа-я ]+")
        if (!regex.matches(source)) {
            ""
        } else {
            null
        }
    }

}
