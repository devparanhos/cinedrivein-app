package br.com.renovatiu.cinedrivein.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class ReportMessagesResponse(
    @SerializedName("tipoMensagem")
    val typeMessage: String,

    @SerializedName("codigoMensagem")
    val codeMessage: String,

    @SerializedName("textoMensagem")
    val text: String,
)
