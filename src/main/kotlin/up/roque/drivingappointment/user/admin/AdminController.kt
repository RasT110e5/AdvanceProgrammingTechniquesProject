package up.roque.drivingappointment.user.admin

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import up.roque.drivingappointment.question.ModifyQuestionDto
import up.roque.drivingappointment.question.QuestionDto
import up.roque.drivingappointment.question.option.ModifyOptionDto
import up.roque.drivingappointment.question.option.OptionDto
import up.roque.drivingappointment.web.security.AdminAuthorized
import up.roque.drivingappointment.web.BaseRestResponse
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/questions")
@AdminAuthorized
class AdminController(private val adminService: AdminService) {

  @GetMapping
  fun getAllQuestions(): ResponseEntity<BaseRestResponse<MutableList<QuestionDto>>> {
    val questions = adminService.findAllQuestions().stream().map { it.toDto() }.collect(Collectors.toList())
    return BaseRestResponse.ok(questions)
  }

  @GetMapping("/{id}")
  fun getQuestion(@PathVariable id: Int): ResponseEntity<BaseRestResponse<QuestionDto>> {
    val question = adminService.getQuestion(id)
    return BaseRestResponse.ok(question.toDto())
  }

  @PostMapping
  fun changeQuestionText(@RequestBody modifyDto: ModifyQuestionDto): ResponseEntity<BaseRestResponse<QuestionDto>> {
    val modifiedQuestion = adminService.modifyQuestion(modifyDto)
    return BaseRestResponse.ok(modifiedQuestion.toDto())
  }

  @GetMapping("/options")
  fun getAllOptions(): ResponseEntity<BaseRestResponse<MutableList<OptionDto>>> {
    val options = adminService.findAllOptions()
    return BaseRestResponse.ok(options.stream().map { it.toDto() }.collect(Collectors.toList()))
  }

  @GetMapping("/options/{id}")
  fun getOption(@PathVariable id: Int): ResponseEntity<BaseRestResponse<OptionDto>> {
    val option = adminService.getOption(id)
    return BaseRestResponse.ok(option.toDto())
  }

  @PostMapping("/options")
  fun modifyOption(@RequestBody modifyDto: ModifyOptionDto): ResponseEntity<BaseRestResponse<OptionDto>> {
    val option = adminService.modifyOption(modifyDto)
    return BaseRestResponse.ok(option.toDto())
  }
}