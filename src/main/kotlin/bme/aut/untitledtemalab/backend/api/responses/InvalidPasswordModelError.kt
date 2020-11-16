package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class InvalidPasswordModelError : ApiModelError(message = "Password's do not match", status = HttpStatus.UNAUTHORIZED)