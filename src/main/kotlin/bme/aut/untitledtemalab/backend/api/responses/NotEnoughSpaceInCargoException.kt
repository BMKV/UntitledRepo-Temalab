package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Not enough space in User's cargo")
class NotEnoughSpaceInCargoException(message: String? = "Not enough space in User's cargo") : RuntimeException(message)