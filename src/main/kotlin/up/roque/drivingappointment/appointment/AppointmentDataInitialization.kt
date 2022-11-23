package up.roque.drivingappointment.appointment

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import up.roque.drivingappointment.appointment.schedule.AppointmentCreator

@Configuration
@Profile("dev")
class AppointmentDataInitialization(
  private val appointmentService: AppointmentService,
) :
  InitializingBean {
  override fun afterPropertiesSet() {
    val creator = AppointmentCreator(appointmentService, 22, 0)
    creator.createAppointmentsForTheNextBusinessDays(3)
  }


}