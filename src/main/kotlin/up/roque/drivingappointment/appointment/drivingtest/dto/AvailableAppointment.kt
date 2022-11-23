package up.roque.drivingappointment.appointment.drivingtest.dto

import java.time.LocalDateTime

interface AvailableAppointment {
  val id: Int
  val time: LocalDateTime
}