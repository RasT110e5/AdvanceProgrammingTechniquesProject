package up.roque.drivingappointment.question.option.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import up.roque.drivingappointment.question.option.Option

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "text", "correct")
data class OptionDto @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
  @JsonProperty("text") val text: String,
  @JsonProperty("correct") val correct: Boolean
) {
  var id: Int? = null

  fun toEntity(): Option {
    val option = Option()
    option.text = this.text
    option.correct = this.correct
    return option
  }
}