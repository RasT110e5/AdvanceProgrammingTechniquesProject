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
    val appointment = appointmentService.getAppointmentBySecret(key)
    if (!appointment.isValidNow()) throw AppointmentIsNotValidForExamNow(appointment)
    val examAttemptOptional = examAttemptRepository.findByAppointment(appointment)
    return if (examAttemptOptional.isPresent)
      examAttemptOptional.get()
    else {
      val examAttempt = ExamAttempt()
      examAttempt.appointment = appointment
      examAttemptRepository.save(examAttempt)
    }
  }

  class AppointmentIsNotValidForExamNow(appointment: DrivingTestAppointment) :
    RuntimeException("Appointment '$appointment' is not valid for exam now, only 1 hour from start time.")
}