package up.roque.drivingappointment.exam

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import up.roque.drivingappointment.appointment.AppointmentService
import up.roque.drivingappointment.user.admin.AdminService
import up.roque.drivingappointment.web.BaseRestResponse
import up.roque.drivingappointment.web.security.StudentAuthorized
import java.security.Principal
import java.util.*

@RestController
@RequestMapping("/exams")
@StudentAuthorized
class ExamController(
  private val examService: ExamService,
  private val adminService: AdminService,
  private val appointmentService: AppointmentService
) {

  @GetMapping("/start")
  fun startExam(
    @RequestParam("key") key: UUID,
    @RequestParam(value = "glasses", required = false) glasses: Boolean,
    principal: Principal
  ): ResponseEntity<BaseRestResponse<ExamStartedDto>> {
    val eyeAppointment = if (glasses) appointmentService.reserveRandomEyeAppointment(principal.name)
    else null
    val examStartedDto = ExamStartedDto(
      examService.startExam(key),
      adminService.findAllQuestions(),
      eyeAppointment?.toDto()
    )
    return BaseRestResponse.ok(examStartedDto)
  }

}