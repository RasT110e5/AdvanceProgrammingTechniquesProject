package up.roque.drivingappointment.data.initialization

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import up.roque.drivingappointment.question.QuestionDto
import up.roque.drivingappointment.user.admin.AdminService

@Configuration
class QuestionDataInitialization(
  private val adminService: AdminService,
  private val mapper: ObjectMapper
) : InitializingBean {

  override fun afterPropertiesSet() {
    val collType = mapper.typeFactory.constructCollectionType(MutableList::class.java, QuestionDto::class.java)
    val questions: MutableList<QuestionDto> =
      mapper.readValue(ClassPathResource("questions-options.json").file, collType)
    for (question in questions)
      adminService.createQuestion(question)
  }

}