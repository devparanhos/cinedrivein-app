package br.com.renovatiu.cinedrivein.domain.usecase.firestore

import br.com.renovatiu.cinedrivein.domain.repository.firestore.FirestoreRepository

class DeleteReportUseCase(
    private val repository: FirestoreRepository
) {
    suspend fun delete(id: String?) {
        repository.deleteReport(id = id)
    }
}