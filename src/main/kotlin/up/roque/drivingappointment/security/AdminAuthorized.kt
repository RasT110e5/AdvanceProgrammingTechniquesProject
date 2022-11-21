package up.roque.drivingappointment.security

import org.springframework.security.access.prepost.PreAuthorize
import java.lang.annotation.Inherited

/**
 * Annotation for specifying a method access-control expression which will be evaluated to
 * decide whether a method invocation is allowed or not.
 *
 * @author Luke Taylor
 * @since 3.0
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@PreAuthorize("hasAuthority('$ADMIN')")
annotation class AdminAuthorized
