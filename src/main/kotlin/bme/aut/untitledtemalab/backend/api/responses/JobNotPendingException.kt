package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Couldn't accept job")
class JobNotPendingException(message: String? = "Couldn't accept job") : RuntimeException(message)