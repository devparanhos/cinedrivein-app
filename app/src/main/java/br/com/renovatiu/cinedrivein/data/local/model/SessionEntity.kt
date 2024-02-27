package br.com.renovatiu.cinedrivein.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("session")
data class SessionEntity(

    @PrimaryKey
    val id: Int? = null,

    @ColumnInfo("time")
    val time: String,

    @ColumnInfo("modality")
    val modality: String,

    @ColumnInfo("cinema_cnpj")
    val cinemaCnpj: String,

    @ColumnInfo("cinema_name")
    val cinemaName: String,

    @ColumnInfo("movie_code")
    val movieCode: String,

    @ColumnInfo("movie_title")
    val movieTitle: String,

    @ColumnInfo("screen_type")
    val screenType: String,

    @ColumnInfo("digital")
    val isDigital: String,

    @ColumnInfo("type_projection")
    val typeProjection: String,

    @ColumnInfo("audio")
    val audio: String,

    @ColumnInfo("subtitle")
    val hasSubtitle: String,

    @ColumnInfo("sign")
    val hasSign: String,

    @ColumnInfo("caption_descriptive")
    val captionDescriptive: String,

    @ColumnInfo("audio_description")
    val audioDescription: String,

    @ColumnInfo("distributor_cnpj")
    val distributorCnpj: String,

    @ColumnInfo("distributor_name")
    val distributorName: String,

    @ColumnInfo("total_seats")
    val totalSeats: Int,

    @ColumnInfo("total_full_quantity")
    val totalFullQuantity: Int,

    @ColumnInfo("total_full_sold")
    val totalFullSold: Float,

    @ColumnInfo("total_half_quantity")
    val totalHalfQuantity: Int,

    @ColumnInfo("total_half_sold")
    val totalHalfSold: Float
)
