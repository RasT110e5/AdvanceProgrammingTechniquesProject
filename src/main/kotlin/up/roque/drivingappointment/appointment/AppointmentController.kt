package up.roque.drivingappointment.appointment

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import up.roque.drivingappointment.appointment.dto.AvailableAppointment
import up.roque.drivingappointment.appointment.dto.ReservedDrivingTestAppointment
import up.roque.drivingappointment.security.StudentAuthorized
import java.security.Principal

@RestController
@RequestMapping("/api/appointments")
@StudentAuthorized
class AppointmentController(private val appointmentService: AppointmentService) {

  @GetMapping
  fun getAllAvailableAppointments(): ResponseEntity<List<AvailableAppointment>> {
    return ResponseEntity.ok(appointmentService.findAllAvailableAppointments())
  }

  @PostMapping("/reserve/{id}")
  fun reserveAppointment(
    @PathVariable(value = "id") id: Int,
    principal: Principal,
  ): ResponseEntity<ReservedDrivingTestAppointment> {
    val reservedAppointment = appointmentService.reserveAppointment(id, principal.name)
    return ResponseEntity.ok(ReservedDrivingTestAppointment.fromEntity(reservedAppointment))
  }
}