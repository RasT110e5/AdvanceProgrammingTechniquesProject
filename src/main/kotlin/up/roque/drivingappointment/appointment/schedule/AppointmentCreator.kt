package up.roque.drivingappointment.appointment.schedule

import up.roque.drivingappointment.appointment.AppointmentService
import java.time.DayOfWeek
import java.time.LocalDate

class AppointmentCreator(
  private val appointmentService: AppointmentService,
  private val amountOfAppointmentsPerDay: Int,
  private val startingHourOfFirstAppointment: Int
  ) {
  fun createAppointmentsForTheNextBusinessDays(days: Int) {
    for (businessDay in getNextBusinessDays(days)) createAvailableAppointmentsForDay(businessDay)
  }

  private fun createAvailableAppointmentsForDay(day: LocalDate) {
    val openingTime = day.atTime(startingHourOfFirstAppointment, 0)
    for (appointmentNumber in 0L..amountOfAppointmentsPerDay) {
      appointmentService.newDrivingTestAppointment(openingTime.plusHours(appointmentNumber))
      appointmentService.newEyeAppointment(openingTime.plusHours(appointmentNumber))
    }
  }

  private fun getNextBusinessDays(numberOfDays: Int): MutableList<LocalDate> {
    val businessDays = mutableListOf<LocalDate>()
    var dayCounter = 0L
    while (businessDays.size < numberOfDays) {
      val day = LocalDate.now().plusDays(dayCounter++)
      if (isBusinessDay(day)) businessDays.add(day)
    }
    return businessDays
  }

  private fun isBusinessDay(day: LocalDate) =
    DayOfWeek.SATURDAY != day.dayOfWeek && DayOfWeek.SUNDAY != day.dayOfWeek
}