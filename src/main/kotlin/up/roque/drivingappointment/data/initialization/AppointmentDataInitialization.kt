package up.roque.drivingappointment.data.initialization

import net.bytebuddy.asm.Advice.Local
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import up.roque.drivingappointment.appointment.AppointmentService
import java.time.DayOfWeek
import java.time.LocalDate

@Configuration
class AppointmentDataInitialization(private val appointmentService: AppointmentService) :
  InitializingBean {
  override fun afterPropertiesSet() {
    val last7BusinessDays = getLast7BusinessDays()
    for (businessDay in last7BusinessDays) createAppointmentsForDay(businessDay)
  }

  private fun createAppointmentsForDay(day: LocalDate) {
    val openingTime = day.atTime(8, 0)
    for (appointmentNumber in 0L..9L) {
      appointmentService.newDrivingTestAppointment(openingTime.plusHours(appointmentNumber))
      appointmentService.newEyeAppointment(openingTime.plusHours(appointmentNumber))
    }
  }

  private fun getLast7BusinessDays(): MutableList<LocalDate> {
    val businessDays = mutableListOf<LocalDate>()
    var dayCounter = 0L
    while (businessDays.size < 7) addIfBusinessDay(dayCounter++, businessDays)
    return businessDays
  }

  private fun addIfBusinessDay(dayCounter: Long, businessDays: MutableList<LocalDate>) {
    val day = LocalDate.now().minusDays(dayCounter)
    if (isBusinessDay(day)) businessDays.add(day)
  }

  private fun isBusinessDay(day: LocalDate) =
    DayOfWeek.SATURDAY != day.dayOfWeek && DayOfWeek.SUNDAY != day.dayOfWeek

}