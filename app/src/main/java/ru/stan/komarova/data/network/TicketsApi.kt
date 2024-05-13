package ru.stan.komarova.data.network

import retrofit2.http.GET
import ru.stan.komarova.domain.model.Tickets

interface TicketsApi {
    @GET("v3/00727197-24ae-48a0-bcb3-63eb35d7a9de")
    suspend fun getTicketsById(): Tickets
}