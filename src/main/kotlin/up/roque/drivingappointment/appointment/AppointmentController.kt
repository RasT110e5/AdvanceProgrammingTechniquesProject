package up.roque.drivingappointment.appointment

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import up.roque.drivingappointment.appointment.dto.AvailableAppointment
import up.roque.drivingappointment.appointment.dto.ReservedDrivingTestAppointment
import up.roque.drivingappointment.security.StudentAuthorized
import up.roque.drivingappointment.web.BaseRestResponse
import java.security.Principal

@RestController
@RequestMapping("/api/appointments")
@StudentAuthorized
class AppointmentController(private val appointmentService: AppointmentService) {

  @GetMapping
  fun getAllAvailableAppointments(): ResponseEntity<BaseRestResponse<List<AvailableAppointment>>> {
    return BaseRestResponse.ok(appointmentService.findAllAvailableForReserveAppointments())
  }

  @PostMapping("/reserve/{id}")
  fun reserveAppointment(
    @PathVariable(value = "id") id: Int,
    principal: Principal,
  ): ResponseEntity<BaseRestResponse<ReservedDrivingTestAppointment>> {
    val reservedAppointment = appointmentService.reserveAppointment(id, principal.name)
    return BaseRestResponse.ok(reservedAppointment.toReservedDto())
  }

  @PostMapping("/free/{id}")
  fun freeAppointment(
    @PathVariable(value = "id") id: Int,
    principal: Principal
  ): ResponseEntity<BaseRestResponse<Nothing?>> {
    appointmentService.freeAppointment(id, principal.name)
    return BaseRestResponse.ok("Appointment is now free")
  }
}