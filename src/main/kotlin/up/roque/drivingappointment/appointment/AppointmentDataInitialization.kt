package up.roque.drivingappointment.appointment

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import up.roque.drivingappointment.appointment.schedule.AppointmentCreator

@Configuration
class AppointmentDataInitialization(
  private val appointmentService: AppointmentService,
) :
  InitializingBean {
  override fun afterPropertiesSet() {
//    val creator = AppointmentCreator(appointmentService, 15, 0)
//    creator.createAppointmentsForTheNextBusinessDays(3)
  }


}