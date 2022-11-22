package up.roque.drivingappointment.appointment

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.stereotype.Repository
import up.roque.drivingappointment.appointment.dto.AvailableAppointment
import up.roque.drivingappointment.user.student.Student
import java.time.LocalDateTime
import java.util.*

@Repository
interface AppointmentRepository : JpaRepository<Appointment, Int>