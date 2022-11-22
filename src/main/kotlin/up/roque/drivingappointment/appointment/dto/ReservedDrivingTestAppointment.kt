package up.roque.drivingappointment.appointment.dto

import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import java.time.LocalDateTime
import java.util.UUID

data class ReservedDrivingTestAppointment(
  val id: Int?,
  val secretKey: UUID,
  val time: LocalDateTime?,
) {

  companion object {
    fun fromEntity(appointment: DrivingTestAppointment): ReservedDrivingTestAppointment {
      return ReservedDrivingTestAppointment(
        appointment.id, appointment.secretKey, appointment.time
      )
    }
  }
}
