package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
class UserNotFoundException(message: String? = "User not found") : RuntimeException(message)