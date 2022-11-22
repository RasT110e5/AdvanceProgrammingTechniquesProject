package up.roque.drivingappointment.appointment

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import up.roque.drivingappointment.appointment.AppointmentService
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import up.roque.drivingappointment.web.security.SecurityService
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.concurrent.Callable
import java.util.function.Consumer
import java.util.function.Function

@Configuration
class AppointmentDataInitialization(
  private val appointmentService: AppointmentService,
  private val securityService: SecurityService,
) :
  InitializingBean {
  override fun afterPropertiesSet() {
    val next7BusinessDays = getNext7BusinessDays()
    for (businessDay in next7BusinessDays) createAvailableAppointmentsForDay(businessDay)
  }

  private fun createAvailableAppointmentsForDay(day: LocalDate) {
    val openingTime = day.atTime(8, 0)
    for (appointmentNumber in 0L..9L) {
      appointmentService.newDrivingTestAppointment(openingTime.plusHours(appointmentNumber))
      appointmentService.newEyeAppointment(openingTime.plusHours(appointmentNumber))
    }
  }

  private fun getNext7BusinessDays(): MutableList<LocalDate> {
    return get7BusinessDaysWith { LocalDate.now().plusDays(it) }
  }

  private fun get7BusinessDaysWith(dayCallable: Function<Long, LocalDate>): MutableList<LocalDate> {
    val businessDays = mutableListOf<LocalDate>()
    var dayCounter = 0L
    while (businessDays.size < 7) {
      val day = dayCallable.apply(dayCounter++)
      if (isBusinessDay(day)) businessDays.add(day)
    }
    return businessDays
  }

  private fun isBusinessDay(day: LocalDate) =
    DayOfWeek.SATURDAY != day.dayOfWeek && DayOfWeek.SUNDAY != day.dayOfWeek

}