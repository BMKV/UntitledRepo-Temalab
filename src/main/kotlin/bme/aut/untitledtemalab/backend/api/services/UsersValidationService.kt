package bme.aut.untitledtemalab.backend.api.services

import bme.aut.untitledtemalab.backend.api.model.UserRegistration
import bme.aut.untitledtemalab.backend.api.responses.*
import bme.aut.untitledtemalab.backend.api.security.encoder
import bme.aut.untitledtemalab.backend.database.UserRepository
import org.springframework.stereotype.Service

@Service
class UsersValidationService(private val userRepository: UserRepository,
                             private val idGeneratorService: IdGeneratorService) {

    fun validateUserRegistration(newUser: UserRegistration) {
        if (!newUser.isValidBody())
            throw InvalidInputModelError()
        if (!newUser.isValidEmailFormat())
            throw InvalidEmailFormatModelError()
        if (userRepository.findAllByEmailAddress(newUser.email.toString()).isNotEmpty())
            throw EmailAlreadyInUseModelError()
    }

    fun validateUserId(userId: Long) {
        if (!idGeneratorService.validateUID(userId))
            throw InvalidUserIdModelError()
        if (!userRepository.existsById(userId))
            throw UserNotFoundModelError()
    }

    fun validateEmailAndPassword(email: String, password: String) {
        val user = userRepository.findAllByEmailAddress(email)
        if (user.isEmpty())
            throw UserNotFoundModelError()
        if (!encoder().matches(password, user.first().password))
            throw InvalidPasswordModelError()
    }

    fun isUserAdmin(userId: Long) {
        if (!userRepository.findById(userId).get().isAdmin)
            throw UserNotAdminModelError()
    }
}