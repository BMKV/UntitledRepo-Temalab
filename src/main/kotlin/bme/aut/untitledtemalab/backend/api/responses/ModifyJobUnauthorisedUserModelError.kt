package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class ModifyJobUnauthorisedUserModelError : ApiModelError(message = "You couldn't modify the Job", status = HttpStatus.CONFLICT)