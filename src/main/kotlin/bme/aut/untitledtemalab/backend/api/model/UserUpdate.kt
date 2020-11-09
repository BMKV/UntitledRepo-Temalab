package bme.aut.untitledtemalab.backend.api.model

import bme.aut.untitledtemalab.backend.database.model.Users

/**
 * Interface for updating an User in the database
 */
interface UserUpdate {
    /**
     * @param password the password to check
     *
     * @return true, if the passwords match, else false
     */
    fun validatePassword(password: String): Boolean

    /**
     * @param dbUser the User in the database
     *
     * @return the changed database User object
     */
    fun updateUser(dbUser: Users): Users
}