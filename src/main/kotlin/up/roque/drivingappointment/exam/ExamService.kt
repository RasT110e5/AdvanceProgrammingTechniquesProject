package up.roque.drivingappointment.exam

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import up.roque.drivingappointment.appointment.AppointmentService
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import java.lang.RuntimeException
import java.util.*

@Service
@Transactional(readOnly = true)
class ExamService(
  private val examAttemptRepository: ExamAttemptRepository,
  private val appointmentService: AppointmentService
) {

  @Transactional
  fun startExam(key: UUID): ExamAttempt {
    val appointment = appointmentService.getValidAppointmentBySecret(key)
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


}