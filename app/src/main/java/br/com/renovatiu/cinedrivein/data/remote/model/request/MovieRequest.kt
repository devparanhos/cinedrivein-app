package br.com.renovatiu.cinedrivein.data.remote.model.request

import br.com.renovatiu.cinedrivein.data.remote.model.request.DistributorRequest
import com.google.gson.annotations.SerializedName

data class MovieRequest(
    @SerializedName("numeroObra")
    val number: String? = null,

    @SerializedName("tituloObra")
    val title: String? = null,

    @SerializedName("tipoTela")
    val typeScreen: String? = null,

    @SerializedName("digital")
    val digital: String? = null,

    @SerializedName("tipoProjecao")
    val typeProjection: String? = null,

    @SerializedName("audio")
    val audio: String? = null,

    @SerializedName("legenda")
    val subtitle: String? = null,

    @SerializedName("libras")
    val signLanguage: String? = null,

    @SerializedName("legendagemDescritiva")
    val captionDescriptive: String? = null,

    @SerializedName("audioDescricao")
    val audioDescription: String? = null,

    @SerializedName("distribuidor")
    val distributor: DistributorRequest? = null
)
