package br.com.renovatiu.cinedrivein.domain.usecase.session

import br.com.renovatiu.cinedrivein.domain.repository.session.SessionRepository

class DeleteSessionUseCase(
    private val repository: SessionRepository
) {
    suspend fun delete(id: Int) {
        repository.deleteSession(id = id)
    }
}