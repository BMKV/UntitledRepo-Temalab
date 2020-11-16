package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

open class ApiModelError(message: String?, status: HttpStatus) : Throwable(message) {
    private val statusCode: HttpStatus = status
    fun getHttpStatusCode(): HttpStatus {
        return statusCode
    }
}