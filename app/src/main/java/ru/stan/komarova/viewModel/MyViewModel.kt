package ru.stan.komarova.viewModel

import android.text.InputFilter
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.stan.komarova.db.Offer
import ru.stan.komarova.db.TicketsApi

class MyViewModel : ViewModel() {
    val offerLiveData = MutableLiveData<List<Offer>>()
    var editTextValue: String = ""

    val messageForFragment: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    fun fetchOffersFromApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val roomApi = retrofit.create(TicketsApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val room = roomApi.getTicketsById()
                offerLiveData.postValue(room.offers)
            } catch (e: Exception) {
                Log.e("MyViewModel", "Error fetching data: ${e.message}")
            }
        }
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
