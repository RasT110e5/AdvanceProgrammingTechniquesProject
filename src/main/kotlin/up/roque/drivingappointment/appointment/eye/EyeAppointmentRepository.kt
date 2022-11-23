package up.roque.drivingappointment.appointment.eye

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import up.roque.drivingappointment.user.student.Student
import java.time.LocalDateTime
import java.util.*

@Repository
interface EyeAppointmentRepository : JpaRepository<EyeAppointment, Int> {
  @Query("select e from EyeAppointment e where e.available = true and e.time > ?1")
  fun findAllAvailableAfter(time: LocalDateTime): MutableList<EyeAppointment>

  @Query("select e from EyeAppointment e where e.available = false and e.student = ?1")
  fun findAllReservedAppointmentsFor(student: Student): MutableList<EyeAppointment>

  fun findFirstByStudentAndTimeIsAfter(student: Student, time: LocalDateTime) : Optional<EyeAppointment>
}
