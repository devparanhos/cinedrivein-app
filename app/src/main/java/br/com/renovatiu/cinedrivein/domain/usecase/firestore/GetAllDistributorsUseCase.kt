package br.com.renovatiu.cinedrivein.domain.usecase.firestore

import br.com.renovatiu.cinedrivein.data.remote.model.request.DistributorRequest
import br.com.renovatiu.cinedrivein.domain.repository.firestore.FirestoreRepository

class GetAllDistributorsUseCase(
    private val repository: FirestoreRepository
) {
    suspend fun getAll() : List<DistributorRequest> {
        return repository.getAllDistributors()
    }
}