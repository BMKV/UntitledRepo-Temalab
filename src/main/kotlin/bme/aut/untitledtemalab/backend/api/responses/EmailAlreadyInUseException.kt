package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Email already in use")
class EmailAlreadyInUseException(message: String? = "Email already in use") : RuntimeException(message)