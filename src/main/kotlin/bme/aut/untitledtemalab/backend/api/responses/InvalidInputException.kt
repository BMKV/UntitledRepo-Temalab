package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "Invalid input")
class InvalidInputException(message: String? = "Invalid input") : RuntimeException(message)