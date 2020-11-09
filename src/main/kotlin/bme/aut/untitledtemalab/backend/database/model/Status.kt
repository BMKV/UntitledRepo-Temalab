package bme.aut.untitledtemalab.backend.database.model

import bme.aut.untitledtemalab.backend.api.model.Job
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class Status(private val value: String)  {
    pending("pending"),
    accepted("accepted"),
    pickedUp("pickedUp"),
    delivered("delivered"),
    expired("expired");

    override fun toString(): String {
        return value
    }

    companion object {
        fun fromValue(text: String): Status? {
            for (b in values()) {
                if (b.value == text) {
                    return b
                }
            }
            return null
        }
    }
}