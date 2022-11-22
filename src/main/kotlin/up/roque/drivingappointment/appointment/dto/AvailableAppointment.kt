package up.roque.drivingappointment.appointment.dto

import java.time.LocalDateTime

interface AvailableAppointment {
  val id: Int
  val time: LocalDateTime
}