package br.com.renovatiu.cinedrivein.di.modules.viewmodel

import br.com.renovatiu.cinedrivein.presentation.feature.report.create.viewmodel.CreateReportViewModel
import br.com.renovatiu.cinedrivein.presentation.feature.report.list.viewmodel.ListReportViewModel
import br.com.renovatiu.cinedrivein.presentation.feature.session.create.viewmodel.CreateSessionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ListReportViewModel(
            ancineAPI = get(),
            getAllProtocolUseCase = get(),
            updateProtocolNumberUseCase = get(),
            updateProtocolStatusUseCase = get()
        )
    }

    viewModel {
        CreateReportViewModel(
            getAllSessionsUseCase = get(),
            createProtocolUseCase = get(),
            deleteAllSessionUseCase = get()
        )
    }

    viewModel {
        CreateSessionViewModel(
            createSessionUseCase = get(),
            getAllDistributorsUseCase = get()
        )
    }
}