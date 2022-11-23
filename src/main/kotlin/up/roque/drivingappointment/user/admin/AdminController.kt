package up.roque.drivingappointment.user.admin

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import up.roque.drivingappointment.appointment.schedule.AppointmentCreatorTask
import up.roque.drivingappointment.web.BaseRestResponse
import up.roque.drivingappointment.web.security.AdminAuthorized

@RestController
@RequestMapping("/api/admin")
@AdminAuthorized
class AdminController(private val creatorTask: AppointmentCreatorTask) {

  @PostMapping("appointments")
  fun runCreateNewAppointments(): ResponseEntity<BaseRestResponse<Nothing?>> {
    creatorTask.createNewAppointments()
    return BaseRestResponse.ok("Running create new appointments task")
  }
}