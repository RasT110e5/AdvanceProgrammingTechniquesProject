package up.roque.drivingappointment.exam

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import up.roque.drivingappointment.appointment.eye.EyeAppointmentDto
import up.roque.drivingappointment.question.Question

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExamStartedDto(
  val examAttempt: ExamAttempt,
  @JsonIgnore val questions: List<Question>,
  val eyeAppointment: EyeAppointmentDto?
) {
  @JsonProperty("questions")
  fun questions(): List<Question> {
    return this.questions.asSequence().shuffled().toList()
  }
}