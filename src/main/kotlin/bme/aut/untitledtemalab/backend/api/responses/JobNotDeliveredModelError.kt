package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class JobNotDeliveredModelError : ApiModelError(message = "Couldn't deliver job", status = HttpStatus.CONFLICT)