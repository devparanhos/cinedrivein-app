package br.com.renovatiu.cinedrivein.domain.repository.firestore

import br.com.renovatiu.cinedrivein.data.remote.model.request.DistributorRequest
import br.com.renovatiu.cinedrivein.data.remote.model.request.ProtocolRequest

interface FirestoreRepository {
    suspend fun createProtocol(protocolRequest: ProtocolRequest) : Boolean
    suspend fun getAllProtocol(startDate: String, endDate: String) : List<ProtocolRequest>
    suspend fun updateProtocolNumber(number: String?, id: String?)
    suspend fun updateProtocolStatus(status: String?, id: String?)
    suspend fun getAllDistributors() : List<DistributorRequest>
    suspend fun deleteReport(id: String?)
}