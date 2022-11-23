package up.roque.drivingappointment.exam

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import java.time.LocalDateTime
import java.util.*

@Repository
interface ExamAttemptRepository : JpaRepository<ExamAttempt, Int> {
  fun findByAppointment(appointment: DrivingTestAppointment): Optional<ExamAttempt>

  @Query("select e from ExamAttempt e where e.appointment.secretKey = ?1 and e.appointment.student.username = ?2")
  fun findByAppointmentKeyAndStudent(key: UUID, student: String): Optional<ExamAttempt>

  @Query("select e from ExamAttempt e where e.appointment.student.username = ?1")
  fun findAllByStudent(username: String): List<ExamAttempt>

  @Query("select e from ExamAttempt e where e.appointment in ?1")
  fun findAllByAppointmentIn(appointments: List<DrivingTestAppointment>): List<ExamAttempt>

  @Query("select e from ExamAttempt e where e.appointment.time < ?1")
  fun findAllOlderThan(date: LocalDateTime): MutableList<ExamAttempt>
}
