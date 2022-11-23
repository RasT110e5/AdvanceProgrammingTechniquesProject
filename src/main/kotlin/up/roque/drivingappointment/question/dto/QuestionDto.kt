package up.roque.drivingappointment.question.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import up.roque.drivingappointment.question.Question
import up.roque.drivingappointment.question.option.dto.OptionDto

@JsonPropertyOrder("id", "text", "options")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class QuestionDto @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
  @JsonProperty("text") val text: String,
  @JsonProperty("options") val options: List<OptionDto>
) {
  var id: Int? = null

  fun toEntity(): Question {
    val question = Question()
    question.text = this.text
    return question
  }
}