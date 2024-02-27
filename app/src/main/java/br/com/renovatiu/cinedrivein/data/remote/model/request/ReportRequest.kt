package br.com.renovatiu.cinedrivein.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class ReportRequest(
    @SerializedName("registroANCINEExibidor")
    val ancineExhibitorCode: String? = null,

    @SerializedName("registroANCINESala")
    val ancineRoomCode: String? = null,

    @SerializedName("diaCinematografico")
    val date: String? = null,

    @SerializedName("houveSessoes")
    val hasSession: String? = null,

    @SerializedName("retificador")
    val rectifing: String? = null,

    @SerializedName("sessoes")
    val sessions: List<SessionRequest>? = null
)
