package up.roque.drivingappointment.exam

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import up.roque.drivingappointment.appointment.AppointmentService
import up.roque.drivingappointment.exam.dto.ExamStatisticsDto
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class ExamStatisticsService(
  private val examService: ExamService,
  private val appointmentService: AppointmentService
) {
  fun calculateStatisticsFor(date: LocalDate): ExamStatisticsDto {
    val pastAppointmentsForDate = appointmentService.findAllPastAppointmentsForDate(date)
    val exams = examService.findAllByAppointments(pastAppointmentsForDate)
    return ExamStatisticsDto(
      exams.size,
      exams.count { it.isApproved() },
      exams.count { !it.isApproved() },
      pastAppointmentsForDate.count { it.wasStudentAbsent() }
    )
  }

}