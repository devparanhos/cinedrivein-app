package br.com.renovatiu.cinedrivein.domain.usecase.firestore

import br.com.renovatiu.cinedrivein.data.remote.model.request.ProtocolRequest
import br.com.renovatiu.cinedrivein.domain.repository.firestore.FirestoreRepository

class GetAllProtocolUseCase(
    private val repository: FirestoreRepository
) {
    suspend fun getAll(startDate: String, endDate: String) : List<ProtocolRequest> {
        return repository.getAllProtocol(
            startDate = startDate,
            endDate = endDate
        )
    }
}