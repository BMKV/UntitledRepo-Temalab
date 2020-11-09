package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Email format is not correct")
class InvalidEmailFormatException(message: String? = "Email format is not correct") : RuntimeException(message)