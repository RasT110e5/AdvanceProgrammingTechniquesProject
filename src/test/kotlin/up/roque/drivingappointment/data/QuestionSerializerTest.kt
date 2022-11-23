package up.roque.drivingappointment.data

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource
import up.roque.drivingappointment.question.dto.QuestionDto

class QuestionSerializerTest {
  private val mapper = ObjectMapper()

  @Test
  @DisplayName("should be able to deserialize questions in json format")
  fun questionSerializerTest_() {
    val jsonFile = ClassPathResource("questions-options.json").file
    val collType = mapper.typeFactory.constructCollectionType(MutableList::class.java, QuestionDto::class.java)
    val questions: MutableList<QuestionDto> = mapper.readValue(jsonFile, collType)
    assertThat(questions).isNotEmpty.hasSize(10)
    for (question in questions)
      assertThat(question.options).isNotEmpty.anyMatch { it.correct }
  }

}