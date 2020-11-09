package bme.aut.untitledtemalab.backend.database

import bme.aut.untitledtemalab.backend.database.model.Jobs
import bme.aut.untitledtemalab.backend.database.model.PackageSize
import bme.aut.untitledtemalab.backend.database.model.Status
import bme.aut.untitledtemalab.backend.database.model.Users
import org.springframework.data.repository.CrudRepository

interface JobRepository : CrudRepository<Jobs, Long> {
    fun findAllByStatus(status: Status): List<Jobs>
    fun findAllByStatusAndSize(status: Status, size: PackageSize): List<Jobs>

    fun findAllByStatusIsLikeAndDeliverer(status: Status, deliverer: Users): List<Jobs>
    fun findAllBySizeIsLikeAndDeliverer(size: PackageSize, deliverer: Users): List<Jobs>
    fun findAllBySizeIsLikeAndStatusIsLikeAndDeliverer(size: PackageSize,status: Status, deliverer: Users): List<Jobs>

    fun findAllByStatusIsLikeAndSender(status: Status, deliverer: Users): List<Jobs>
    fun findAllBySizeIsLikeAndSender(size: PackageSize, deliverer: Users): List<Jobs>
    fun findAllBySizeIsLikeAndStatusIsLikeAndSender(size: PackageSize,status: Status, deliverer: Users): List<Jobs>
}