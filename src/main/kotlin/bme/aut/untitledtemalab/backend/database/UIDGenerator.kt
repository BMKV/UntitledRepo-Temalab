package bme.aut.untitledtemalab.backend.database

import kotlin.random.Random

object UIDGenerator {
    fun generateUID(): Long {
        return 1000000000 + Random.nextLong(8999999999)
    }

    fun validateUID(userId: Long): Boolean {
        return (userId in 1000000000..9999999999)
    }
}