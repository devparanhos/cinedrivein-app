package br.com.renovatiu.cinedrivein.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class RevenueRequest(
    @SerializedName("codigoModalidadePagamento")
    val paymentType: Int? = null,

    @SerializedName("valorArrecadado")
    val totalRecieved: Float? = null
)
