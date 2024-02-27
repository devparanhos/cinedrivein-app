package br.com.renovatiu.cinedrivein.data.remote.model.request

data class ProtocolRequest(
    val id: String? = null,
    val protocol: String? = null,
    val status: String? = null,
    val date: String? = null,
    val ancineExhibitorCode: String? = null,
    val ancineRoomCode: String? = null,
    val report: ReportRequest? = null
)
