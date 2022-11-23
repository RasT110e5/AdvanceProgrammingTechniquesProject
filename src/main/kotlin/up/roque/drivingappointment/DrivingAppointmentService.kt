package up.roque.drivingappointment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class DrivingAppointmentService

fun main(args: Array<String>) {
	runApplication<DrivingAppointmentService>(*args)
}
