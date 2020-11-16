package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class NotEnoughSpaceInCargoModelError : ApiModelError(message = "Not enough space in User's cargo", status = HttpStatus.CONFLICT)