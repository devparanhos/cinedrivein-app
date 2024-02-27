package br.com.renovatiu.cinedrivein.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class CinemaRequest(
    @SerializedName("cnpj")
    val cnpj: String? = null,

    @SerializedName("razaoSocial")
    val cinemaName: String? = null
)