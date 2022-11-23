package up.roque.drivingappointment.question

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import up.roque.drivingappointment.user.admin.QuestionService

@Configuration
class QuestionDataInitialization(
  private val questionService: QuestionService,
  private val mapper: ObjectMapper
) : InitializingBean {

  override fun afterPropertiesSet() {
    val collType = mapper.typeFactory.constructCollectionType(MutableList::class.java, QuestionDto::class.java)
    val questions: MutableList<QuestionDto> =
      mapper.readValue(ClassPathResource("questions-options.json").file, collType)
    for (question in questions)
      questionService.createQuestion(question)
  }

}