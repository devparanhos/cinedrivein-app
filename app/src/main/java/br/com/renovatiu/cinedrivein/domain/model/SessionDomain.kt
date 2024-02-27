package br.com.renovatiu.cinedrivein.domain.model

data class SessionDomain(
    val id: Int? = null,
    val time: String,
    val modality: String,
    val cinemaCnpj: String,
    val cinemaName: String,
    val movieCode: String,
    val movieTitle: String,
    val screenType: String,
    val isDigital: String,
    val typeProjection: String,
    val audio: String,
    val hasSubtitle: String,
    val hasSign: String,
    val captionDescriptive: String,
    val audioDescription: String,
    val distributorCnpj: String,
    val distributorName: String,
    val totalSeats: Int,
    val totalFullQuantity: Int,
    val totalFullSold: Float,
    val totalHalfQuantity: Int,
    val totalHalfSold: Float
)
