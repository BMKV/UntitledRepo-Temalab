package bme.aut.untitledtemalab.backend.database

import bme.aut.untitledtemalab.backend.database.model.Routes
import org.springframework.data.repository.CrudRepository

interface RouteRepository : CrudRepository<Routes, Long>