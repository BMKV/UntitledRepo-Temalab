package bme.aut.untitledtemalab.backend.database.model

import javax.persistence.*

@Entity
@Table(name = "package_sent")
class PackageSent(
        @Id
        @GeneratedValue
        @Column(name = "id")
        val id: Long,

        @Column(name = "job_id")
        val jobId: Long,

        @Column(name = "user_id")
        val userId: Long,
)