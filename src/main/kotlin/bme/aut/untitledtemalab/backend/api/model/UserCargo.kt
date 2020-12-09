package bme.aut.untitledtemalab.backend.api.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Information on the Users cargo
 */
class UserCargo(
        /**
         * The actual size of the cargo
         * @return cargoFreeSize
         */
        @field:JsonProperty("cargoFreeSize") var cargoFreeSize: Int,

        /**
         * The maximal size of the cargo
         * @return cargoMaxSize
         */
        @field:JsonProperty("cargoMaxSize") var cargoMaxSize: Int
)
