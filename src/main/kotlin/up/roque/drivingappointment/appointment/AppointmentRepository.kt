package up.roque.drivingappointment.appointment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import up.roque.drivingappointment.appointment.dto.AvailableAppointment

@NoRepositoryBean
interface AppointmentRepository<T : Appointment> : JpaRepository<T, Int> {
  @Query("select d from DrivingTestAppointment d where d.available = true")
  fun findAllAvailable(): MutableList<AvailableAppointment>
}