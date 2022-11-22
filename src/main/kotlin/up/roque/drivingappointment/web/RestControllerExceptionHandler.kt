package up.roque.drivingappointment.web

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import up.roque.drivingappointment.appointment.AppointmentService
import up.roque.drivingappointment.user.admin.AdminService

@RestControllerAdvice
class RestControllerExceptionHandler {
  private val log = LoggerFactory.getLogger(this.javaClass)

  @ExceptionHandler
  fun handleAppointmentAlreadyReserved(ex: AppointmentService.AppointmentAlreadyReserved)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Appointment already reserved exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleAppointmentNotFound(ex: AppointmentService.NoAppointmentFoundForId)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Appointment not found exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleReservationNotFound(ex: AppointmentService.NoReservedAppointmentForId)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Reserved appointment not found exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleNoQuestionFound(ex: AdminService.NoQuestionForId)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Question not found exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleNoOptionFound(ex: AdminService.NoOptionForId)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Option not found exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }
}