package br.com.renovatiu.cinedrivein.data.repository.firestore

import br.com.renovatiu.cinedrivein.data.remote.model.request.DistributorRequest
import br.com.renovatiu.cinedrivein.data.remote.model.request.ProtocolRequest
import br.com.renovatiu.cinedrivein.domain.repository.firestore.FirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class FirestoreRepositoryImpl(
    private val firestore: FirebaseFirestore
) : FirestoreRepository {
    override suspend fun createProtocol(protocolRequest: ProtocolRequest) : Boolean {

        try {
            val protocolRef = firestore.collection("protocols")
            val result = protocolRef.add(protocolRequest).await()

            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun getAllProtocol(startDate: String, endDate: String) : List<ProtocolRequest> {
        val protocolRef = firestore.collection("protocols")
        val protocols = mutableListOf<ProtocolRequest>()
        val result = protocolRef
            .whereGreaterThanOrEqualTo("date", startDate)
            .whereLessThanOrEqualTo("date", endDate)
            .orderBy("date", Query.Direction.ASCENDING)
            .orderBy("protocol", Query.Direction.ASCENDING)
            .get()
            .await()

        for (document in result) {
            val protocol = document.toObject(ProtocolRequest::class.java)
            protocols.add(protocol)
        }

        return protocols
    }

    override suspend fun updateProtocolNumber(number: String?, id: String?) {
        val protocolRef = firestore.collection("protocols")
        val query = protocolRef
            .whereEqualTo("id", id)
            .get()
            .await()

        if (query.documents.isNotEmpty()) {
            for(document in query) {
                protocolRef.document(document.id).update("protocol", number)
            }
        }
    }

    override suspend fun updateProtocolStatus(status: String?, id: String?) {
        val protocolRef = firestore.collection("protocols")
        val query = protocolRef
            .whereEqualTo("id", id)
            .get()
            .await()

        if (query.documents.isNotEmpty()) {
            for(document in query) {
                protocolRef.document(document.id).update("status", status)
            }
        }
    }

    override suspend fun getAllDistributors(): List<DistributorRequest> {
        val distributorsRef = firestore.collection("distributors")
        val distributors = mutableListOf<DistributorRequest>()
        val result = distributorsRef
            .orderBy("distributorName", Query.Direction.ASCENDING)
            .get()
            .await()

        for (document in result) {
            val distributor = document.toObject(DistributorRequest::class.java)
            distributors.add(distributor)
        }

        return distributors
    }

    override suspend fun deleteReport(id: String?) {
        val protocolRef = firestore.collection("protocols")
        val query = protocolRef
            .whereEqualTo("id", id)
            .get()
            .await()

        if (query.documents.isNotEmpty()) {
            for(document in query) {
                protocolRef.document(document.id).delete().await()
            }
        }
    }
}