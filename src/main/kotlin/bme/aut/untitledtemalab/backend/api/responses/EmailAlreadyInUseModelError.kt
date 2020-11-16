package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class EmailAlreadyInUseModelError : ApiModelError(message = "Email already in use", status = HttpStatus.CONFLICT)