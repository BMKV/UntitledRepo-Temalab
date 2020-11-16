package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class InvalidInputModelError : ApiModelError(message = "Invalid input", status = HttpStatus.METHOD_NOT_ALLOWED)