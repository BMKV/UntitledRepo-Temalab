package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class CantAcceptJobModelError : ApiModelError(message = "You can't accept the Job", status = HttpStatus.CONFLICT)