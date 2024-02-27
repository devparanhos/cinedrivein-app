package br.com.renovatiu.cinedrivein.domain.usecase.session

import br.com.renovatiu.cinedrivein.core.handler.HandlerRequest
import br.com.renovatiu.cinedrivein.domain.model.SessionDomain
import br.com.renovatiu.cinedrivein.domain.repository.session.SessionRepository

class CreateSessionUseCase(
    private val repository: SessionRepository
) {
    suspend fun create(session: SessionDomain) : HandlerRequest {
        return repository.create(session = session)
    }
}