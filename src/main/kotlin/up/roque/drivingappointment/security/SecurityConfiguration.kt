package up.roque.drivingappointment.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfiguration {

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    http.authorizeRequests().antMatchers("/h2-console/**").permitAll().and()
      .authorizeRequests().anyRequest().authenticated().and().httpBasic()

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    http.csrf().disable().cors().disable()
    http.headers().frameOptions().disable()
    return http.build()
  }
}