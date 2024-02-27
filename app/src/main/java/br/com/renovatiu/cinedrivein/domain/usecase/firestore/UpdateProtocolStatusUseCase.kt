package br.com.renovatiu.cinedrivein.domain.usecase.firestore

import br.com.renovatiu.cinedrivein.domain.repository.firestore.FirestoreRepository

class UpdateProtocolStatusUseCase(
    private val repository: FirestoreRepository
) {
    suspend fun update(status: String?, id: String?) {
        repository.updateProtocolStatus(
            status = status,
            id = id
        )
    }
}