package br.com.renovatiu.cinedrivein.data.repository.session

import br.com.renovatiu.cinedrivein.core.handler.HandlerRequest
import br.com.renovatiu.cinedrivein.data.local.dao.SessionDao
import br.com.renovatiu.cinedrivein.data.local.mapper.session.toDomain
import br.com.renovatiu.cinedrivein.data.local.mapper.session.toEntity
import br.com.renovatiu.cinedrivein.domain.model.SessionDomain
import br.com.renovatiu.cinedrivein.domain.repository.session.SessionRepository

class SessionRepositoryImpl(
    private val sessionDao: SessionDao
) : SessionRepository {
    override suspend fun create(session: SessionDomain) : HandlerRequest {
        return try {
            when(sessionDao.insert(sessionEntity = session.toEntity())){
                0L -> {
                    HandlerRequest.Error(message = "Não foi possível salvar o registro")
                }

                else -> {
                    HandlerRequest.Success()
                }
            }
        } catch (exception: Exception) {
            HandlerRequest.Error(message = exception.message)
        }
    }

    override suspend fun getAll(): List<SessionDomain> {
        return try {
            sessionDao.getAll().map { it.toDomain() }
        } catch (exception: Exception) {
            listOf()
        }
    }

    override suspend fun deleteAll() {
        sessionDao.deleteAll()
    }

}