package br.com.renovatiu.cinedrivein.domain.usecase.session

import br.com.renovatiu.cinedrivein.domain.model.SessionDomain
import br.com.renovatiu.cinedrivein.domain.repository.session.SessionRepository

class GetAllSessionsUseCase(
    private val repository: SessionRepository
) {
    suspend fun getAll() : List<SessionDomain>{
        return repository.getAll()
    }
}