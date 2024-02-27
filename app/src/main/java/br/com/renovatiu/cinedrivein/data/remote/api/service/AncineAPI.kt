package br.com.renovatiu.cinedrivein.data.remote.api.service

import br.com.renovatiu.cinedrivein.data.remote.model.request.ReportRequest
import br.com.renovatiu.cinedrivein.data.remote.model.response.ConsultReportProtocolResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AncineAPI {

    @GET("protocolos/{id}")
    suspend fun getReportResultById(@Path("id") id: String?) : Response<ConsultReportProtocolResponse>

    @POST("bilheterias")
    suspend fun sendReport(@Body report: ReportRequest) : Response<ConsultReportProtocolResponse>

}