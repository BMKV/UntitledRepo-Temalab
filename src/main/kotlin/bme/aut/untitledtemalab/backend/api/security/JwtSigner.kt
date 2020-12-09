package bme.aut.untitledtemalab.backend.api.security

import bme.aut.untitledtemalab.backend.database.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.KeyPair
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class JwtSigner {
    val keyPair: KeyPair = Keys.keyPairFor(SignatureAlgorithm.RS256)
    val issuer = "backend"

    @Autowired
    lateinit var userRepository: UserRepository

    fun createJwt(userId: String): JwtSigned {
        val date = Date.from(Instant.now().plus(Duration.ofMinutes(15)))
        val token = Jwts.builder()
                .signWith(keyPair.private, SignatureAlgorithm.RS256)
                .setSubject(userId)
                .setIssuer(issuer)
                .setExpiration(date)
                .setIssuedAt(Date.from(Instant.now()))
                .compact()

        println("\n\nCreating token...\nThe token is:$token\n\n")

        return JwtSigned(token, date)
    }

    fun validateToken(token: String): Boolean {
        return try {
            userRepository.existsByEmailAddress(extractAllClaims(token).subject.toString())
        }
        catch (e: Exception)    {
            false
        }
    }

    fun extractUsername(token: String): String? {
        return try {
            extractAllClaims(token).subject.toString()
        }
        catch (e: Exception) {
            null
        }
    }


    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(keyPair.private).requireIssuer(issuer).build().parseClaimsJws(token).body
    }

    inner class JwtSigned(val jwt: String, val date: Date)
}