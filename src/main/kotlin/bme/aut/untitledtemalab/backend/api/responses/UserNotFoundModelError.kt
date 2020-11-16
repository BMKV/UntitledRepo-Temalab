package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class UserNotFoundModelError : ApiModelError(message = "User not found", status = HttpStatus.NOT_FOUND)