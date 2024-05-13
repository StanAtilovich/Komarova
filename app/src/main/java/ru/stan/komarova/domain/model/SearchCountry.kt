package ru.stan.komarova.domain.model

data class SearchCountry(
    val tickets_offers: List<TicketsOffer>
)

data class PriceSearch(
    val value: Int
)

data class TicketsOffer(
    val id: Int,
    val price: Price,
    val time_range: List<String>,
    val title: String
)