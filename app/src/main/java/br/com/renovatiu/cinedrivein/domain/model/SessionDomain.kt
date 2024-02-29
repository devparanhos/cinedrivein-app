package br.com.renovatiu.cinedrivein.domain.model

data class SessionDomain(
    val id: Int? = null,
    val time: String = "",
    val modality: String = "",
    val cinemaCnpj: String = "",
    val cinemaName: String = "",
    val movieCode: String = "",
    val movieTitle: String = "",
    val screenType: String = "",
    val isDigital: String = "",
    val typeProjection: String = "",
    val audio: String = "",
    val hasSubtitle: String = "",
    val hasSign: String = "",
    val captionDescriptive: String = "",
    val audioDescription: String = "",
    val distributorCnpj: String = "",
    val distributorName: String = "",
    val totalSeats: Int = 0,
    val totalFullQuantity: Int = 0,
    val totalFullSold: Float = 0f,
    val totalHalfQuantity: Int = 0,
    val totalHalfSold: Float = 0f
)
