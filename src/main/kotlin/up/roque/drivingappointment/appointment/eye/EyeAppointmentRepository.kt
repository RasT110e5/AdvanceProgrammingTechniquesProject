package up.roque.drivingappointment.appointment.eye

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EyeAppointmentRepository : JpaRepository<EyeAppointment, Int>