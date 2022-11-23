package up.roque.drivingappointment.exam

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import up.roque.drivingappointment.exam.dto.ExamAttemptDto
import up.roque.drivingappointment.exam.dto.ExamAttemptInProgressDto
import up.roque.drivingappointment.exam.dto.ExamStartedDto
import up.roque.drivingappointment.exam.dto.ExamStatisticsDto
import up.roque.drivingappointment.question.QuestionService
import up.roque.drivingappointment.web.BaseRestResponse
import up.roque.drivingappointment.web.security.AdminAuthorized
import up.roque.drivingappointment.web.security.StudentAuthorized
import java.security.Principal
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/api/exams")
class ExamController(
  private val examService: ExamService,
  private val questionService: QuestionService,
  private val examStatisticsService: ExamStatisticsService
) {

  @GetMapping
  fun getAllExamAttemptsFor(principal: Principal)
          : ResponseEntity<BaseRestResponse<List<ExamAttemptDto>>> {
    val exams = examService.findAllForStudent(principal.name)
    return BaseRestResponse.ok(exams.map { it.toDto() }.toList())
  }

  @PostMapping
  @StudentAuthorized
  fun answerQuestionOnExamAttempt(
    @RequestParam("key") key: UUID,
    @RequestParam("option") optionId: Int,
    principal: Principal
  ): ResponseEntity<BaseRestResponse<ExamAttemptInProgressDto>> {
    val examAttempt = examService.selectOptionOnExamWithKeyAndStudent(key, principal.name, optionId)
    return BaseRestResponse.ok(examAttempt.toInProgressDto())
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

  @GetMapping("/results")
  @AdminAuthorized
  fun getExamStatistics(
    @RequestParam("date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
  ): ResponseEntity<BaseRestResponse<ExamStatisticsDto>> {
    val examStats = examStatisticsService.calculateStatisticsFor(date)
    return BaseRestResponse.ok(examStats)
  }

  @GetMapping("/results/all")
  @AdminAuthorized
  fun getAllExamAttempts(): ResponseEntity<BaseRestResponse<List<ExamAttempt>>> {
    val exams = examService.findAll()
    return BaseRestResponse.ok(exams)
  }

}