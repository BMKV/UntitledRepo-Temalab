package bme.aut.untitledtemalab.backend.geoapi

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.util.UriUtils
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import kotlin.math.ceil


object GeoApi {

    private const val OpenRouteApiKey: String = "5b3ce3597851110001cf6248321c9befa8e14af59658024fcdbebfa0"

    private const val defaultDeliveryTimeInDays: Int = 3

    fun getOptimalRouteTime(startLocation: String, destination: String): String {
        return optimalTimeApproximationFromDistance(distanceFromGeoCoords(geoCoordsFromAddress(startLocation), geoCoordsFromAddress(destination)))
    }

    private fun optimalTimeApproximationFromDistance(distance: Double): String {
        return if (distance < 200.0)
            defaultDeliveryTimeInDays.toString()
        else (defaultDeliveryTimeInDays + additionalDeliveryDaysFromDistance(distance)).toString()
    }

    private fun additionalDeliveryDaysFromDistance(distance: Double): Int {
        return ceil((distance - 200) / 300).toInt()
    }

    private fun geoCoordsFromAddress(address: String): String {
        val client: Client = ClientBuilder.newClient()
        val response: Response = client.target("https://api.openrouteservice.org/geocode/search?api_key=$OpenRouteApiKey&text=" + UriUtils.encode(address, Charsets.UTF_8))
                .request(MediaType.TEXT_PLAIN_TYPE)
                .header("Accept", "application/json; charset=utf-8")
                .get()

        return ObjectMapper().readTree(response.readEntity(String::class.java)).path("features")[0]["geometry"]["coordinates"].toString()
    }

    private fun distanceFromGeoCoords(startLocation: String, destination: String): Double {
        val client: Client = ClientBuilder.newClient()
        val payload: Entity<String> = Entity.json("{ \"locations\":[$startLocation, $destination], \"metrics\":[\"distance\"], \"units\":\"km\" }")
        val response: Response = client.target("https://api.openrouteservice.org/v2/matrix/driving-car")
                .request()
                .header("Authorization", OpenRouteApiKey)
                .header("Accept", "application/json; charset=utf-8")
                .header("Content-Type", "application/json; charset=utf-8")
                .post(payload)

        val distanceMatrix = ObjectMapper().readTree(response.readEntity(String::class.java)).path("distances")

        val distance1 = distanceMatrix[0][1].asDouble()
        val distance2 = distanceMatrix[1][0].asDouble()

        return  (distance1 + distance2)/2
    }
}