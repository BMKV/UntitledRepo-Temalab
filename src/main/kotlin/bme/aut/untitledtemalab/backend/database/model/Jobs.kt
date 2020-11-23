package bme.aut.untitledtemalab.backend.database.model

import bme.aut.untitledtemalab.backend.database.PostgreSQLEnumType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Table(name = "jobs")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType::class)
class Jobs(

        @Id
        @Column(name = "job_id")
        val id: Long,

        @NotNull
        @Column(name = "name", nullable = false)
        var name: String,

        @Enumerated(EnumType.STRING)
        @NotNull
        @Type(type = "pgsql_enum")
        @Column(name = "status", nullable = false)
        var status: Status,

        @Min(1)
        @Max(5)
        @Column(name = "sender_rating")
        var senderRating: Int? = null,

        @Enumerated(EnumType.STRING)
        @NotNull
        @Column(name = "size", nullable = false)
        @Type(type = "pgsql_enum")
        var size: PackageSize,

        @NotNull
        @Column(name = "payment", nullable = false)
        var payment: Int,

        @NotNull
        @Column(name = "job_issued_date", nullable = false)
        var jobIssuedDate: String,

        @NotNull
        @Column(name = "deadline", nullable = false)
        var deadline: String,

        @Column(name = "delivery_date")
        var deliveryDate: String? = null,

        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "delivery_route", referencedColumnName = "route_id")
        val deliveryRoute: Routes,

        @OneToOne
        @JoinTable(
                name = "package_delivered",
                joinColumns = [JoinColumn(name = "job_id", referencedColumnName = "job_id")],
                inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")])
        var deliverer: Users? = null,

        @OneToOne
        @JoinTable(
                name = "package_sent",
                joinColumns = [JoinColumn(name = "job_id", referencedColumnName = "job_id")],
                inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")])
        var sender: Users
)