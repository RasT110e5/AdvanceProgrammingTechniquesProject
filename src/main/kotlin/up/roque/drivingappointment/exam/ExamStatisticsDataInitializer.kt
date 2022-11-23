package up.roque.drivingappointment.exam

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Profile
import up.roque.drivingappointment.appointment.AppointmentService
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointmentRepository
import up.roque.drivingappointment.question.QuestionService
import up.roque.drivingappointment.question.option.Option
import up.roque.drivingappointment.user.student.Student
import up.roque.drivingappointment.web.security.SecurityService
import java.time.LocalDate
import java.time.LocalDateTime

@Configuration
@DependsOn(value = ["userDataInitialization"])
@Profile("dev")
class ExamStatisticsDataInitializer(
  private val appointmentService: AppointmentService,
  private val securityService: SecurityService,
  private val appointmentRepository: DrivingTestAppointmentRepository,
  private val questionService: QuestionService,
  private val examAttemptRepository: ExamAttemptRepository
) : InitializingBean {
  override fun afterPropertiesSet() {
    createNormalStatisticDataForYesterday()
  }

  private fun createNormalStatisticDataForYesterday() {
    val timeFromYesterday = LocalDate.now().minusDays(1).atTime(8, 0)
    createAbsentStudentData(timeFromYesterday)
    createAtLimitStudentData(timeFromYesterday)
  }

  private fun createAtLimitStudentData(timeFromYesterday: LocalDateTime) {
    val atLimitStudent = securityService.getStudent("student2")
    createExamFor(atLimitStudent, timeFromYesterday.plusHours(2))
    { newExamWithSelectedOptions(it, getIncorrectOptions()) }
    createExamFor(atLimitStudent, timeFromYesterday.plusHours(3))
    { newExamWithSelectedOptions(it, getIncorrectOptions()) }
    createExamFor(atLimitStudent, timeFromYesterday.plusHours(4))
    { newExamWithSelectedOptions(it, getCorrectOptions()) }
  }

  private fun createExamFor(
    atLimitStudent: Student,
    time: LocalDateTime,
    examCreator: (DrivingTestAppointment) -> Unit
  ) {
    val appointment = appointmentService.newDrivingTestAppointment(time)
    appointmentService.reserveAppointment(appointment.id!!, atLimitStudent.username!!)
    examCreator.invoke(appointment)
  }

  private fun newExamWithSelectedOptions(appointment: DrivingTestAppointment, selectedOptions: MutableSet<Option>) {
    val exam = ExamAttempt()
    exam.appointment = appointment
    exam.options = selectedOptions
    examAttemptRepository.save(exam)
  }

  private fun getIncorrectOptions(): MutableSet<Option> {
    return listOf(1, 5, 8, 10, 13, 16, 20, 24, 27, 31)
      .map { questionService.getOption(it) }.toMutableSet()
  }

  private fun getCorrectOptions(): MutableSet<Option> {
    return listOf(3, 4, 7, 12, 15, 17, 19, 23, 30, 34)
      .map { questionService.getOption(it) }.toMutableSet()
  }


  private fun createAbsentStudentData(timeFromYesterday: LocalDateTime) {
    val absentStudent = securityService.getStudent("student3")
    createAbsentAppointmentFor(absentStudent, timeFromYesterday)
    createAbsentAppointmentFor(absentStudent, timeFromYesterday.plusHours(1))
  }

  private fun createAbsentAppointmentFor(absentStudent: Student, time: LocalDateTime) {
    val appointment = appointmentService.newDrivingTestAppointment(time)
    appointment.reserve(absentStudent)
    appointmentRepository.save(appointment)
  }
}