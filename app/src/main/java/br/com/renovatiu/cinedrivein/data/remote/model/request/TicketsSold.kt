package br.com.renovatiu.cinedrivein.data.remote.model.request

import br.com.renovatiu.cinedrivein.data.remote.model.request.RevenueRequest
import com.google.gson.annotations.SerializedName

data class TicketsSold(
    @SerializedName("codigoCategoriaIngresso")
    val ticketType: String? = null,

    @SerializedName("quantidadeEspectadores")
    val quantitySold: Int? = null,

    @SerializedName("totalizacoesModalidadePagamento")
    val revenue: List<RevenueRequest>? = null
)
