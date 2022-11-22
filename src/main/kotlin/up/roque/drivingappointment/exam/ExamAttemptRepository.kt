package up.roque.drivingappointment.exam

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import java.util.*

@Repository
interface ExamAttemptRepository : JpaRepository<ExamAttempt, Int> {
  fun findByAppointment(appointment: DrivingTestAppointment): Optional<ExamAttempt>
}
