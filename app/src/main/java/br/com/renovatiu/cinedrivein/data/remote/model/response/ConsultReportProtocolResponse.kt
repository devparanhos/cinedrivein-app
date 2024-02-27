package br.com.renovatiu.cinedrivein.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class ConsultReportProtocolResponse(
    @SerializedName("registroANCINEExibidor")
    val ancineExhibitorCode: String,

    @SerializedName("registroANCINESala")
    val ancineRoomCode: String,

    @SerializedName("diaCinematografico")
    val date: String,

    @SerializedName("numeroProtocolo")
    val protocolNumber: String,

    @SerializedName("statusProtocolo")
    val status: String,

    @SerializedName("mensagens")
    val messages: List<ReportMessagesResponse>
)
