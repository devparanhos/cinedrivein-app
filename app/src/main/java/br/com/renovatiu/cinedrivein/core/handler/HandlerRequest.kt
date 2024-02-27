package br.com.renovatiu.cinedrivein.core.handler

sealed class HandlerRequest {
    data class Success(val data: Any? = null) : HandlerRequest()
    data class Error(val data: Any? = null, val message: String?) : HandlerRequest()
}
