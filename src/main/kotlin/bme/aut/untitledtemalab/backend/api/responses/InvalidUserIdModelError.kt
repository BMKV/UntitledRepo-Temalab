package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class InvalidUserIdModelError : ApiModelError(message = "Invalid User ID supplied", status = HttpStatus.BAD_REQUEST)