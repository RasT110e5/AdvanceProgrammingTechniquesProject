package up.roque.drivingappointment.question.option.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ModifyOptionDto @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
  @JsonProperty("id") val id: Int,
  @JsonProperty("newText") val newText: String,
  @JsonProperty("correct") val correct: Boolean,
  @JsonProperty("questionId") val questionId: Int
)
