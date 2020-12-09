package bme.aut.untitledtemalab.backend.api.services

import bme.aut.untitledtemalab.backend.database.UserRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class IdGeneratorService(private val userRepository: UserRepository) {

    fun generateUID(): Long {
        var possibleId = Random.nextLong(1000000000, 9999999999)
        while (userRepository.existsById(possibleId))
            possibleId = Random.nextLong(1000000000, 9999999999)
        return possibleId
    }

    fun validateUID(userId: Long): Boolean {
        return (userId in 1000000000..9999999999)
    }
}