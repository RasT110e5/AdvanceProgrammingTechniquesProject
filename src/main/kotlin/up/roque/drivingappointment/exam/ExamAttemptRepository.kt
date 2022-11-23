package up.roque.drivingappointment.exam

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import up.roque.drivingappointment.user.student.Student
import java.util.*

@Repository
interface ExamAttemptRepository : JpaRepository<ExamAttempt, Int> {
  fun findByAppointment(appointment: DrivingTestAppointment): Optional<ExamAttempt>

  @Query("select e from ExamAttempt e where e.appointment.secretKey = ?1 and e.appointment.student.username = ?2")
  fun findByAppointmentKeyAndStudent(key: UUID, student: String): Optional<ExamAttempt>
}
