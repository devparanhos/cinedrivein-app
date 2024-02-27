package br.com.renovatiu.cinedrivein.domain.usecase.session

import br.com.renovatiu.cinedrivein.domain.repository.session.SessionRepository

class DeleteAllSessionUseCase(
    private val repository: SessionRepository
) {
    suspend fun deleteAll() {
        repository.deleteAll()
    }
}