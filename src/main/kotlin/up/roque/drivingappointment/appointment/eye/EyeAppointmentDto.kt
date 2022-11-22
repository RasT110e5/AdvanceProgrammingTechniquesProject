package up.roque.drivingappointment.appointment.eye

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EyeAppointmentDto(
  val id: Int?,
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  val time: LocalDateTime?,
  val doctorName: String?
)