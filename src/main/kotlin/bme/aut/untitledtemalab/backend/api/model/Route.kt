package bme.aut.untitledtemalab.backend.api.model

import com.fasterxml.jackson.annotation.JsonProperty
/**
 * A Route for a specific Job
 */
class Route(dbRoute: bme.aut.untitledtemalab.backend.database.model.Routes?) {

    /**
     * The time the Route was completed
     * @return actualTime
     */
    @JsonProperty("actualTime")
    var actualTime: Long? = null

    /**
     * The optimal time for the Route
     * @return optimalTime
     */
    @JsonProperty("optimalTime")
    var optimalTime: Long? = null

    /**
     * The start location of the Route
     * @return startLocation
     */
    @JsonProperty("startLocation")
    var startLocation: String? = null

    /**
     * The destination of the Route
     * @return destination
     */
    @JsonProperty("destination")
    var destination: String? = null

    init {
        if (dbRoute != null)    {
            this.actualTime = dbRoute.actualTime?.toLong()
            this.optimalTime = dbRoute.optimalTime?.toLong()
            this.startLocation = dbRoute.startLocation
            this.destination = dbRoute.destination
        }
    }
}