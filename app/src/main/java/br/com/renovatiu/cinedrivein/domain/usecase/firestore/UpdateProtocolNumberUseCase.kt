package br.com.renovatiu.cinedrivein.domain.usecase.firestore

import br.com.renovatiu.cinedrivein.domain.repository.firestore.FirestoreRepository

class UpdateProtocolNumberUseCase(
    private val repository: FirestoreRepository
) {
    suspend fun update(number: String?, id: String?) {
        repository.updateProtocolNumber(
            number = number,
            id = id
        )
    }
}