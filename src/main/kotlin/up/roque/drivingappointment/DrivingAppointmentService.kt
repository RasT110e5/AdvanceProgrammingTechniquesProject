package up.roque.drivingappointment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DrivingAppointmentService

fun main(args: Array<String>) {
	runApplication<DrivingAppointmentService>(*args)
}
