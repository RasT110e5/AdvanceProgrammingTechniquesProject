package up.roque.drivingappointment.appointment.eye

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import up.roque.drivingappointment.appointment.AppointmentRepository

@Repository
interface EyeAppointmentRepository : AppointmentRepository<EyeAppointment>