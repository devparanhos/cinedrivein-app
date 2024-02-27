package br.com.renovatiu.cinedrivein.data.remote.model.request

import br.com.renovatiu.cinedrivein.data.remote.model.request.CinemaRequest
import br.com.renovatiu.cinedrivein.data.remote.model.request.MovieRequest
import com.google.gson.annotations.SerializedName

data class SessionRequest(
    @SerializedName("dataHoraInicio")
    val time: String? = null,

    @SerializedName("modalidade")
    val modality: String? = null,

    @SerializedName("vendedorRemoto")
    val cinema: CinemaRequest? = null,

    @SerializedName("obras")
    val movies: List<MovieRequest>? = null,

    @SerializedName("totalizacoesTipoAssento")
    val seats: List<Seat>? = null
)
