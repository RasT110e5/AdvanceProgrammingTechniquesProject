package up.roque.drivingappointment.appointment

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.function.Function

@Configuration
class AppointmentDataInitialization(
  private val appointmentService: AppointmentService,
) :
  InitializingBean {
  override fun afterPropertiesSet() {
    val next3BusinessDays = getNextBusinessDays(3)
    for (businessDay in next3BusinessDays) createAvailableAppointmentsForDay(businessDay)
  }

  private fun createAvailableAppointmentsForDay(day: LocalDate) {
    val openingTime = day.atTime(8, 0)
    for (appointmentNumber in 0L..9L) {
      appointmentService.newDrivingTestAppointment(openingTime.plusHours(appointmentNumber))
      appointmentService.newEyeAppointment(openingTime.plusHours(appointmentNumber))
    }
  }

  private fun getNextBusinessDays(numberOfDays: Int): MutableList<LocalDate> {
    return getBusinessDaysWith(numberOfDays) { LocalDate.now().plusDays(it) }
  }

  private fun getBusinessDaysWith(numberOfDays: Int, dayCallable: Function<Long, LocalDate>): MutableList<LocalDate> {
    val businessDays = mutableListOf<LocalDate>()
    var dayCounter = 0L
    while (businessDays.size < numberOfDays) {
      val day = dayCallable.apply(dayCounter++)
      if (isBusinessDay(day)) businessDays.add(day)
    }
    return businessDays
  }

  private fun isBusinessDay(day: LocalDate) =
    DayOfWeek.SATURDAY != day.dayOfWeek && DayOfWeek.SUNDAY != day.dayOfWeek

}