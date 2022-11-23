package up.roque.drivingappointment.exam

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import up.roque.drivingappointment.appointment.AppointmentService
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import up.roque.drivingappointment.user.admin.QuestionService
import up.roque.drivingappointment.web.security.SecurityService
import java.lang.RuntimeException
import java.util.*

@Service
@Transactional(readOnly = true)
class ExamService(
  private val examAttemptRepository: ExamAttemptRepository,
  private val appointmentService: AppointmentService,
  private val questionService: QuestionService,
  private val securityService: SecurityService
) {

  @Transactional
  fun startExamWithGlasses(key: UUID, username: String): ExamAttempt {
    appointmentService.reserveRandomEyeAppointment(username)
    return startExam(key, username)
  }

  @Transactional
  fun startExam(key: UUID, username: String): ExamAttempt {
    val appointment = appointmentService.getValidAppointmentBySecretAndUsername(key, username)
    appointmentService.reportStudentAttendance(appointment)
    return getExamAttempt(appointment)
  }

  private fun getExamAttempt(appointment: DrivingTestAppointment): ExamAttempt {
    val examAttemptOptional = examAttemptRepository.findByAppointment(appointment)
    return if (examAttemptOptional.isPresent) examAttemptOptional.get()
    else createNewExamAttempt(appointment)
  }

  private fun createNewExamAttempt(appointment: DrivingTestAppointment): ExamAttempt {
    val examAttempt = ExamAttempt()
    examAttempt.appointment = appointment
    return examAttemptRepository.save(examAttempt)
  }

  fun getValidExamAttemptFor(key: UUID, username: String): ExamAttempt {
    return examAttemptRepository.findByAppointmentKeyAndStudent(key, username)
      .orElseThrow { NoValidExamAttemptForUsername(key, username) }
  }

  @Transactional
  fun selectOptionOnExamWithKeyAndStudent(key: UUID, username: String, optionId: Int): ExamAttempt {
    val examAttempt = getValidExamAttemptFor(key, username)
    examAttempt.addSelectedOption(questionService.getOption(optionId))
    return examAttemptRepository.save(examAttempt)
  }

  class NoValidExamAttemptForUsername(key: UUID, username: String) :
    RuntimeException("There is no valid exam attempt with key:$key, and assigned to student:$username")
}