package bme.aut.untitledtemalab.backend.api.responses

import org.springframework.http.HttpStatus

class UserNotAdminModelError : ApiModelError(message = "User does not have admin privilege", status = HttpStatus.UNAUTHORIZED)