package br.com.renovatiu.cinedrivein.di.modules.usecase

import br.com.renovatiu.cinedrivein.domain.usecase.firestore.CreateProtocolUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.firestore.GetAllDistributorsUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.firestore.GetAllProtocolUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.firestore.UpdateProtocolNumberUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.firestore.UpdateProtocolStatusUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.session.CreateSessionUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.session.DeleteAllSessionUseCase
import br.com.renovatiu.cinedrivein.domain.usecase.session.GetAllSessionsUseCase
import org.koin.dsl.module

val usecaseModule = module {

    factory {
        CreateSessionUseCase(
            repository = get()
        )
    }

    factory {
        GetAllSessionsUseCase(
            repository = get()
        )
    }

    factory {
        CreateProtocolUseCase(
            repository = get()
        )
    }

    factory {
        GetAllProtocolUseCase(
            repository = get()
        )
    }

    factory {
        UpdateProtocolNumberUseCase(
            repository = get()
        )
    }

    factory {
        UpdateProtocolStatusUseCase(
            repository = get()
        )
    }

    factory {
        GetAllDistributorsUseCase(
            repository = get()
        )
    }

    factory {
        DeleteAllSessionUseCase(
            repository = get()
        )
    }
}