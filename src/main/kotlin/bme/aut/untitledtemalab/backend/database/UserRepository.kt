package bme.aut.untitledtemalab.backend.database

import bme.aut.untitledtemalab.backend.database.model.Users
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<Users, Long>{
    fun findAllByEmailAddress(emailAddress: String): List<Users>
}