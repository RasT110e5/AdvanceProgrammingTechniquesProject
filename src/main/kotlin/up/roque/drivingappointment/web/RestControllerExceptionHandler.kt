package up.roque.drivingappointment.web

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import up.roque.drivingappointment.appointment.AppointmentService

@RestControllerAdvice
class RestControllerExceptionHandler {
  private val log = LoggerFactory.getLogger(this.javaClass)

  @ExceptionHandler
  fun handleAppointmentAlreadyReserved(ex: AppointmentService.AppointmentAlreadyReserved)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Appointment already reserved exception thrown")
    return BaseRestResponse.badRequest("Appointment is already reserved")
  }
}