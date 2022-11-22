package up.roque.drivingappointment.appointment.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ReservedTestAppointment(
  val id: Int?,
  val secretKey: UUID? = null,
  val time: LocalDateTime?,
  val available: Boolean,
  val doctorName: String? = null
)