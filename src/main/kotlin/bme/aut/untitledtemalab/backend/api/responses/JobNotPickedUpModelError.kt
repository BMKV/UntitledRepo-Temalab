package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class JobNotPickedUpModelError : ApiModelError(message = "Couldn't pick up job", status = HttpStatus.CONFLICT)