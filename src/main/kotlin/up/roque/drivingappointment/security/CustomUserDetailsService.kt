package up.roque.drivingappointment.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import up.roque.drivingappointment.user.BaseUser

@Component
class CustomUserDetailsService(private val securityService: SecurityService) : UserDetailsService {
  private val log = LoggerFactory.getLogger(this.javaClass)

  override fun loadUserByUsername(username: String?): UserDetails {
    log.info("Authenticating username:$username")
    if (username == null) throw UsernameNotFoundException("The username cannot be null")
    return attemptLoad(username)
  }

  private fun attemptLoad(username: String): UserDetails {
    var user: User? = null
    securityService.findStudent(username)
      .ifPresent { user = newUserWith(it, SimpleGrantedAuthority(STUDENT)) }
    securityService.findAdmin(username)
      .ifPresent { user = newUserWith(it, SimpleGrantedAuthority(ADMIN)) }
    log.info("Authentication ended with: $user")
    return user ?: throw UsernameNotFoundException("User with username:$username could not be found")
  }

  fun newUserWith(baseUser: BaseUser, authority: SimpleGrantedAuthority): User {
    return User(baseUser.username, baseUser.secret, listOf(authority))
  }
}