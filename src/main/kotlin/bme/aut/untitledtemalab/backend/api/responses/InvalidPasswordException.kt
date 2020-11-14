package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Password's do not match")
class InvalidPasswordException(message: String = "Password's do not match") : RuntimeException(message)