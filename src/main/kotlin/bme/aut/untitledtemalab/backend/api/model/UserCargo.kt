package bme.aut.untitledtemalab.backend.api.model

import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Information on the User&#x27;s cargo
 */
class UserCargo(freeSize: Int, maxSize: Int) {
    /**
     * The actual size of the cargo
     * @return cargoFreeSize
     */
    @JsonProperty("cargoFreeSize")
    var cargoFreeSize: Int? = freeSize

    /**
     * The maximal size of the cargo
     * @return cargoMaxSize
     */
    @JsonProperty("cargoMaxSize")
    var cargoMaxSize: Int? = maxSize
}
