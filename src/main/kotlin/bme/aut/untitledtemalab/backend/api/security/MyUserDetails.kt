package bme.aut.untitledtemalab.backend.api.security

import bme.aut.untitledtemalab.backend.database.model.Users
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MyUserDetails(user: Users) : UserDetails {
    var id: Long = user.id
    private val username = user.emailAddress
    private val password = user.password
    private val active = true
    private val authorities: List<GrantedAuthority>


    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return active
    }

    companion object {
        private const val serialVersionUID = 275347623L
    }

    init {
        val roles = mutableListOf<SimpleGrantedAuthority>()
        roles.add(SimpleGrantedAuthority("SENDER"))

        if (user.isAdmin)
            roles.add(SimpleGrantedAuthority("ADMIN"))

        if (user.canDeliver)
            roles.add(SimpleGrantedAuthority("DELIVERER"))

        authorities = roles
    }
}
