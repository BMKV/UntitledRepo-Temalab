package bme.aut.untitledtemalab.backend.api.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var filter: JwtAuthenticationFilter

    override fun configure(http: HttpSecurity?) {
        http?.cors()?.disable()?.csrf()?.disable()
                ?.authorizeRequests()?.antMatchers("/user/login", "/user/register")?.permitAll()
                ?.anyRequest()?.authenticated()
                ?.and()?.exceptionHandling()
                ?.and()?.sessionManagement()
                ?.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http?.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)

    }
}
