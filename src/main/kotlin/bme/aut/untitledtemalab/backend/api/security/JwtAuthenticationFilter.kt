package bme.aut.untitledtemalab.backend.api.security


import bme.aut.untitledtemalab.backend.database.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {
    @Autowired
    lateinit var jwtSigner: JwtSigner

    @Autowired
    lateinit var userRepository: UserRepository

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain) {

        val authorizationHeader = request.getHeader("Authorization")
        var username: String? = null
        var jwt: String? = null
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7)
            username = jwtSigner.extractUsername(jwt)
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = MyUserDetails(userRepository.findAllByEmailAddress(username).first())
            if (jwt?.let { jwtSigner.validateToken(it) } == true) {

                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }

        }
        // Pass request down the chain, except for OPTIONS
        if ("OPTIONS" != request.method) {
            filterChain.doFilter(request, response)
        }

        // filterChain.doFilter(request, response)
    }
}