package up.roque.drivingappointment.security

import org.springframework.security.access.prepost.PreAuthorize
import java.lang.annotation.Inherited

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@PreAuthorize("hasAuthority('$STUDENT')")
annotation class StudentAuthorized
