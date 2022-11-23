package up.roque.drivingappointment.appointment.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import up.roque.drivingappointment.appointment.AppointmentService

@Component
class AppointmentCreatorTask(
  private val appointmentService: AppointmentService
) {

  @Scheduled(cron = "0 1 0 * * MON")
  fun createNewAppointments() {
    val creator = AppointmentCreator(appointmentService, 15, 8)
    creator.createAppointmentsForTheNextBusinessDays(7)
  }

}