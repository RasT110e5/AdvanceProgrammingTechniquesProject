package up.roque.drivingappointment.appointment.drivingtest

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import up.roque.drivingappointment.appointment.dto.AvailableAppointment
import up.roque.drivingappointment.user.student.Student
import java.time.LocalDateTime
import java.util.*

@Repository
interface DrivingTestAppointmentRepository : JpaRepository<DrivingTestAppointment, Int> {
  @Query("select d from DrivingTestAppointment d where d.available = true and d.time > ?1")
  fun findAllAvailableAfter(time: LocalDateTime): MutableList<AvailableAppointment>

  @Query("select d from DrivingTestAppointment d where d.available = false and d.student = ?1 and d.id = ?2")
  fun findReservedAppointmentFor(student: Student, id: Int): Optional<DrivingTestAppointment>

  @Query("select d from DrivingTestAppointment d where d.available = false and d.student = ?1")
  fun findAllReservedAppointmentsFor(student: Student): MutableList<DrivingTestAppointment>

  fun findBySecretKey(secretKey: UUID): Optional<DrivingTestAppointment>
}
