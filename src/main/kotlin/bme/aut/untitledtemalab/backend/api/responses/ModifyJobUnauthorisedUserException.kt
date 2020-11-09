package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "You couldn't modify the Job")
class ModifyJobUnauthorisedUserException(message: String? = "You couldn't modify the Job") : RuntimeException(message)