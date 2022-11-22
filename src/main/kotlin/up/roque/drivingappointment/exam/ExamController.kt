package up.roque.drivingappointment.exam

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import up.roque.drivingappointment.question.Question
import up.roque.drivingappointment.user.admin.AdminService
import up.roque.drivingappointment.web.BaseRestResponse
import up.roque.drivingappointment.web.security.StudentAuthorized
import java.util.*

@RestController
@RequestMapping("/exams")
@StudentAuthorized
class ExamController(
  private val examService: ExamService,
  private val adminService: AdminService
) {

  @GetMapping("/start")
  fun startExam(@RequestParam("key") key: UUID): ResponseEntity<BaseRestResponse<ExamStartedDto>> {
    val examAttempt = examService.startExam(key)
    val questions = adminService.findAllQuestions()
    val examStartedDto = ExamStartedDto(examAttempt, questions)
    return BaseRestResponse.ok(examStartedDto)
  }

  data class ExamStartedDto(val examAttempt: ExamAttempt, @JsonIgnore val questions: List<Question>) {
    @JsonProperty("questions")
    fun questions(): List<Question> {
      return this.questions.asSequence().shuffled().toList()
    }
  }

}