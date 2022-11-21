package up.roque.drivingappointment.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class CustomAuthenticationConfiguration {

  @Autowired
  fun configureAuthenticationManager(
    auth: AuthenticationManagerBuilder,
    authService: SecurityService,
    passwordEncoder: PasswordEncoder,
  ) {
    auth.eraseCredentials(true).userDetailsService(authService).passwordEncoder(passwordEncoder)
  }
}