package up.roque.drivingappointment.appointment

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointmentRepository
import up.roque.drivingappointment.appointment.schedule.AppointmentCreator
import up.roque.drivingappointment.user.student.Student
import up.roque.drivingappointment.web.security.SecurityService
import java.time.LocalDate
import java.time.LocalDateTime

@Configuration
@Profile("dev")
class AppointmentDataInitialization(
  private val appointmentService: AppointmentService,
) : InitializingBean {
  override fun afterPropertiesSet() {
    val creator = AppointmentCreator(appointmentService, 22, 0)
    creator.createAppointmentsForTheNextBusinessDays(3)
  }

}