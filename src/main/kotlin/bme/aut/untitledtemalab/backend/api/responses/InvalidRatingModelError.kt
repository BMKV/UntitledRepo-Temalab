package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class InvalidRatingModelError : ApiModelError(message = "Rating is out of bounds", status =  HttpStatus.UNPROCESSABLE_ENTITY)