package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class JobNotAcceptedModelError : ApiModelError(message = "Couldn't accept job", status = HttpStatus.CONFLICT)