package up.roque.drivingappointment.exam

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import up.roque.drivingappointment.user.admin.QuestionService
import up.roque.drivingappointment.web.BaseRestResponse
import up.roque.drivingappointment.web.security.StudentAuthorized
import java.security.Principal
import java.util.*

@RestController
@RequestMapping("/exams")
class ExamController(
  private val examService: ExamService,
  private val questionService: QuestionService,
) {

  @PostMapping
  @StudentAuthorized
  fun answerQuestionOnExamAttempt(
    @RequestParam("key") key: UUID,
    @RequestParam("option") optionId: Int,
    principal: Principal
  ): ResponseEntity<BaseRestResponse<ExamAttemptDto>> {
    val examAttempt = examService.selectOptionOnExamWithKeyAndStudent(key, principal.name, optionId)
    return BaseRestResponse.ok(examAttempt.toDto())
  }

  @GetMapping("/start")
  @StudentAuthorized
  fun startExam(
    @RequestParam("key") key: UUID,
    @RequestParam(value = "glasses", required = false) glasses: Boolean,
    principal: Principal
  ): ResponseEntity<BaseRestResponse<ExamStartedDto>> {
    val examAttempt = if (glasses) examService.startExamWithGlasses(key, principal.name)
    else examService.startExam(key, principal.name)
    return BaseRestResponse.ok(ExamStartedDto(examAttempt, questionService.findAllQuestions()))
  }

}