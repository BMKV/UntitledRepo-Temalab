package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class InvalidEmailFormatModelError : ApiModelError(message = "Email format is not correct", status =  HttpStatus.UNPROCESSABLE_ENTITY)