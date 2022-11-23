package up.roque.drivingappointment.question.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ModifyQuestionDto @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
  @JsonProperty("id") val id:Int,
  @JsonProperty("newText") val newText: String
)
