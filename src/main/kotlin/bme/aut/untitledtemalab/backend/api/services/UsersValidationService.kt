package bme.aut.untitledtemalab.backend.api.services

import bme.aut.untitledtemalab.backend.api.model.UserRegistration
import bme.aut.untitledtemalab.backend.api.responses.*
import bme.aut.untitledtemalab.backend.api.security.encoder
import bme.aut.untitledtemalab.backend.database.UIDGenerator
import bme.aut.untitledtemalab.backend.database.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsersValidationService {

    @Autowired
    private lateinit var userRepository: UserRepository

    fun validateUserRegistration(newUser: UserRegistration) {
        if (!newUser.isValidBody())
            throw InvalidInputModelError()
        if (!newUser.isValidEmailFormat())
            throw InvalidEmailFormatModelError()
        if (userRepository.findAllByEmailAddress(newUser.email.toString()).isNotEmpty())
            throw EmailAlreadyInUseModelError()
    }

    fun validateUserId(userId: Long) {
        if (!UIDGenerator.validateUID(userId))
            throw InvalidUserIdModelError()
        if (!userRepository.existsById(userId))
            throw UserNotFoundModelError()
    }

    fun validateEmailAndPassword(email: String, password: String) {
        val user = userRepository.findAllByEmailAddress(email)
        if (user.isEmpty())
            throw UserNotFoundModelError()
        if (!encoder().matches(password,user.first().password))
            throw InvalidPasswordModelError()
    }

    fun isUserAdmin(userId: Long) {
        if (!userRepository.findById(userId).get().isAdmin)
            throw UserNotAdminModelError()
    }
}