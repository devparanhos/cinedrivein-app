package br.com.renovatiu.cinedrivein.di.modules.repository

import br.com.renovatiu.cinedrivein.data.repository.firestore.FirestoreRepositoryImpl
import br.com.renovatiu.cinedrivein.data.repository.session.SessionRepositoryImpl
import br.com.renovatiu.cinedrivein.domain.repository.firestore.FirestoreRepository
import br.com.renovatiu.cinedrivein.domain.repository.session.SessionRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<SessionRepository> {
        SessionRepositoryImpl(
            sessionDao = get()
        )
    }

    single<FirestoreRepository> {
        FirestoreRepositoryImpl(
            firestore = get()
        )
    }

}