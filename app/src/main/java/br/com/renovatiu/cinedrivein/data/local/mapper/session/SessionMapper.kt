package br.com.renovatiu.cinedrivein.data.local.mapper.session

import br.com.renovatiu.cinedrivein.data.local.model.SessionEntity
import br.com.renovatiu.cinedrivein.domain.model.SessionDomain

fun SessionDomain.toEntity() : SessionEntity {
    return SessionEntity(
        time = this.time,
        modality = this.modality,
        cinemaCnpj = this.cinemaCnpj,
        cinemaName = this.cinemaName,
        movieCode = this.movieCode,
        movieTitle = this.movieTitle,
        screenType = this.screenType,
        isDigital = this.isDigital,
        typeProjection = this.typeProjection,
        audio = this.audio,
        hasSubtitle = this.hasSubtitle,
        hasSign = this.hasSign,
        captionDescriptive = this.captionDescriptive,
        audioDescription = this.audioDescription,
        distributorName = this.distributorName,
        distributorCnpj = this.distributorCnpj,
        totalSeats = this.totalSeats,
        totalFullQuantity = this.totalFullQuantity,
        totalFullSold = this.totalFullSold,
        totalHalfQuantity = this.totalHalfQuantity,
        totalHalfSold = this.totalHalfSold
    )
}

fun SessionEntity.toDomain() : SessionDomain {
    return SessionDomain(
        id = this.id,
        time = this.time,
        modality = this.modality,
        cinemaCnpj = this.cinemaCnpj,
        cinemaName = this.cinemaName,
        movieCode = this.movieCode,
        movieTitle = this.movieTitle,
        screenType = this.screenType,
        isDigital = this.isDigital,
        typeProjection = this.typeProjection,
        audio = this.audio,
        hasSubtitle = this.hasSubtitle,
        hasSign = this.hasSign,
        captionDescriptive = this.captionDescriptive,
        audioDescription = this.audioDescription,
        distributorName = this.distributorName,
        distributorCnpj = this.distributorCnpj,
        totalSeats = this.totalSeats,
        totalFullQuantity = this.totalFullQuantity,
        totalFullSold = this.totalFullSold,
        totalHalfQuantity = this.totalHalfQuantity,
        totalHalfSold = this.totalHalfSold
    )
}