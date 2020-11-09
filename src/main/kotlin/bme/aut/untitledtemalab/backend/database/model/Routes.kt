package bme.aut.untitledtemalab.backend.database.model

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "routes")
class Routes (
        @Id
        @Column(name="route_id")
        val id: Long,

        @Column(name="actual_time", length = 50)
        var actualTime: String? = null,

        @Column(name="optimal_time", length = 50)
        var optimalTime: String? = null,

        @NotNull
        @Column(name="start_location", nullable = false, length = 50)
        var startLocation: String,

        @NotNull
        @Column(name="destination", nullable = false, length = 50)
        var destination: String
)
