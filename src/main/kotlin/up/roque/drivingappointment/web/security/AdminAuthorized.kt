package up.roque.drivingappointment.web.security

import org.springframework.security.access.prepost.PreAuthorize
import java.lang.annotation.Inherited

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@PreAuthorize("hasAuthority('$ADMIN')")
annotation class AdminAuthorized
