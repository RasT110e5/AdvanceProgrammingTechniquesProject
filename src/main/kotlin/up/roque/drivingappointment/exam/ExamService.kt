package up.roque.drivingappointment.exam

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import up.roque.drivingappointment.appointment.AppointmentService
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import up.roque.drivingappointment.user.admin.QuestionService
import up.roque.drivingappointment.web.security.StudentAuthorized
import java.lang.RuntimeException
import java.util.*

@Service
@Transactional(readOnly = true)
class ExamService(
  private val examAttemptRepository: ExamAttemptRepository,
  private val appointmentService: AppointmentService,
  private val questionService: QuestionService
) {

  @Transactional
  fun startExamWithGlasses(key: UUID, username: String): ExamAttempt {
    if (findExamAttemptBySecretKeyAndUsername(key, username).isEmpty)
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
    val validExamAttempt = findExamAttemptBySecretKeyAndUsername(key, username)
      .orElseThrow { NoValidExamAttemptForUsername(key, username) }
    if (!validExamAttempt.isValidNow()) throw ExamIsNoLongerValidForModification()
    return validExamAttempt
  }

  private fun findExamAttemptBySecretKeyAndUsername(key: UUID, username: String) =
    examAttemptRepository.findByAppointmentKeyAndStudent(key, username)

  @Transactional
  fun selectOptionOnExamWithKeyAndStudent(key: UUID, username: String, optionId: Int): ExamAttempt {
    val examAttempt = getValidExamAttemptFor(key, username)
    examAttempt.addSelectedOption(questionService.getOption(optionId))
    if (examIsCompletedAndFailed(examAttempt)) appointmentService.reserveDrivingTestAppointment(username)
    return examAttemptRepository.save(examAttempt)
  }

  private fun examIsCompletedAndFailed(examAttempt: ExamAttempt) =
    examAttempt.getRespondedQuestions().size == 10 && !examAttempt.isApproved()

  @StudentAuthorized
  fun findAllForStudent(username: String): List<ExamAttempt> {
    return examAttemptRepository.findAllByStudent(username)
  }

  fun findAllByAppointments(appointments: List<DrivingTestAppointment>): List<ExamAttempt> {
    return examAttemptRepository.findAllByAppointmentIn(appointments)
  }

  class NoValidExamAttemptForUsername(key: UUID, username: String) :
    RuntimeException("There is no valid exam attempt with key:$key, and assigned to student:$username")

  class ExamIsNoLongerValidForModification() :
    RuntimeException("This exam is no longer during the valid timeframe for modification (during the appointment)")
}