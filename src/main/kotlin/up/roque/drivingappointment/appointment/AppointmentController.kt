package up.roque.drivingappointment.appointment

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import up.roque.drivingappointment.appointment.dto.AvailableAppointment
import up.roque.drivingappointment.appointment.dto.ReservedTestAppointment
import up.roque.drivingappointment.appointment.eye.EyeAppointment
import up.roque.drivingappointment.web.security.StudentAuthorized
import up.roque.drivingappointment.web.BaseRestResponse
import java.security.Principal
import java.util.stream.Collectors

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
  ): ResponseEntity<BaseRestResponse<ReservedTestAppointment>> {
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

  @GetMapping("/reserved")
  fun getAllReservedAppointmentsFor(principal: Principal)
          : ResponseEntity<BaseRestResponse<MutableList<ReservedTestAppointment>>> {
    val appointments = appointmentService.findAllReservedAppointmentsFor(principal.name)
    return BaseRestResponse.ok(toReservedDto(appointments))
  }

  private fun toReservedDto(appointments: MutableList<Appointment>): MutableList<ReservedTestAppointment> {
    return appointments.stream().map {
      if (it is DrivingTestAppointment) it.toReservedDto()
      else (it as EyeAppointment).toReservedDto()
    }.collect(Collectors.toList())
  }
}