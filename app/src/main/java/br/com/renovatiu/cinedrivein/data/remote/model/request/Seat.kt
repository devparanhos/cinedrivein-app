package br.com.renovatiu.cinedrivein.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class Seat(
    @SerializedName("codigoTipoAssento")
    val seatType: String? = null,

    @SerializedName("quantidadeDisponibilizada")
    val quantitySeats: Int? = null,

    @SerializedName("totalizacoesCategoriaIngresso")
    val ticketsSold: List<TicketsSold>? = null
)
