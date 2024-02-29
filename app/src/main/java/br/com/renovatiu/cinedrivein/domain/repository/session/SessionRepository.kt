package br.com.renovatiu.cinedrivein.domain.repository.session

import br.com.renovatiu.cinedrivein.core.handler.HandlerRequest
import br.com.renovatiu.cinedrivein.domain.model.SessionDomain

interface SessionRepository {
    suspend fun create(session: SessionDomain) : HandlerRequest
    suspend fun getAll() : List<SessionDomain>
    suspend fun deleteAll()
    suspend fun deleteSession(id: Int)
}