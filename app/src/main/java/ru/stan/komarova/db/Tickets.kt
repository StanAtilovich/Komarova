package ru.stan.komarova.db

data class Tickets(
    val offers: List<Offer>
)
data class Offer(
    val id: Int,
    val price: Price,
    val title: String,
    val town: String
)


data class Price(
    val value: Int
)