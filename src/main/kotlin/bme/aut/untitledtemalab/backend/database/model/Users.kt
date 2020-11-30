package bme.aut.untitledtemalab.backend.database.model

import javax.persistence.*
import javax.validation.constraints.*

@Entity
@Table(name = "users")
class Users(
        @Id
        @Column(name = "user_id")
        val id: Long,

        @NotNull
        @Email
        @Column(name = "email_address", unique = true, nullable = false, length = 50)
        var emailAddress: String,

        @NotNull
        @Column(name = "password", nullable = false, length = 50)
        var password: String,

        @NotNull
        @Column(name = "is_admin", nullable = false)
        var isAdmin: Boolean = false,

        @NotNull
        @Column(name = "can_deliver", nullable = false)
        var canDeliver: Boolean,

        @DecimalMin(1.0.toString())
        @DecimalMax(5.0.toString())
        @Column(name = "user_rating")
        var userRating: Float? = null,

        @Column(name = "cargo_free_size")
        var cargoFreeSize: Int = 0,

        @Column(name = "cargo_max_size")
        var cargoMaxSize: Int = 0,

        @OneToMany
        @JoinTable(
                name = "package_delivered",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "job_id", referencedColumnName = "job_id")])
        var packageDelivered: MutableList<Jobs> = mutableListOf(),

        @OneToMany
        @JoinTable(
                name = "package_sent",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "job_id", referencedColumnName = "job_id")])
        var packageSent: MutableList<Jobs> = mutableListOf()
)