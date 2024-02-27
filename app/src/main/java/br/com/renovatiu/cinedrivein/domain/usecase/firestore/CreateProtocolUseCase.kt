package br.com.renovatiu.cinedrivein.domain.usecase.firestore

import br.com.renovatiu.cinedrivein.data.remote.model.request.ProtocolRequest
import br.com.renovatiu.cinedrivein.domain.repository.firestore.FirestoreRepository

class CreateProtocolUseCase(
    private val repository: FirestoreRepository
) {
    suspend fun create(protocol: ProtocolRequest) : Boolean {
        return repository.createProtocol(protocol)
    }
}