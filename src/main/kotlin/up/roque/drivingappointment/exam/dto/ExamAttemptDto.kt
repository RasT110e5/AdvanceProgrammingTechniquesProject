package up.roque.drivingappointment.exam.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import up.roque.drivingappointment.question.option.Option
import up.roque.drivingappointment.question.option.OptionDto

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExamAttemptDto(
  val id: Int?,
  val approved: Boolean,
  @JsonIgnore
  val options: Set<Option>
) {
  @JsonProperty("answers")
  fun getSelectedAnswers(): ArrayList<Answer> {
    val answers = ArrayList<Answer>()
    for (entry in options.associateBy { it.question }) {
      val selectedOption = entry.value.toDto()
      answers.add(Answer(entry.key?.text, OptionAsAnswerDto(selectedOption.text, selectedOption.correct)))
    }
    return answers
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  data class Answer(
    val question: String?,
    val selectedOption: OptionAsAnswerDto
  )

  data class OptionAsAnswerDto(val text: String, val correct: Boolean)
}