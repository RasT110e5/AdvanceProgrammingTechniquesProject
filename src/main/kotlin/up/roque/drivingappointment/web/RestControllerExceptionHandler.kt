package up.roque.drivingappointment.web

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import up.roque.drivingappointment.appointment.AppointmentService
import up.roque.drivingappointment.exam.ExamService
import up.roque.drivingappointment.user.admin.QuestionService

@RestControllerAdvice
class RestControllerExceptionHandler {
  private val log = LoggerFactory.getLogger(this.javaClass)

  @ExceptionHandler
  fun handleUnidentifiedException(ex: Exception)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.error("Unidentified exception thrown", ex)
    return BaseRestResponse.internalError("Unknown error occurred during the request, contact support.")
  }

  @ExceptionHandler
  fun handleParameterIsMissingException(ex: MissingServletRequestParameterException)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.error("Unidentified exception thrown", ex)
    return BaseRestResponse.badRequest(ex.message)
  }

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
  fun handleAppointmentNotFoundBySecret(ex: AppointmentService.NoAppointmentFoundForKey)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Appointment not found by key exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleReservationNotFound(ex: AppointmentService.NoReservedAppointmentForId)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Reserved appointment not found exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleNoQuestionFound(ex: QuestionService.NoQuestionForId)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Question not found exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleNoOptionFound(ex: QuestionService.NoOptionForId)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Option not found exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleNoUsernameFound(ex: UsernameNotFoundException)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Username not found exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleAppointmentIsNotValid(ex: AppointmentService.AppointmentIsNotValidForExamNow)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Appointment is not valid for exam exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleAppointmentIsNotValid(ex: AppointmentService.NoEyeAppointmentIsAvailable)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("No eye appointment is available to start exam with glasses exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleNoValidExamAttemptForUsername(ex: ExamService.NoValidExamAttemptForUsername)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("No valid exam attempt for username exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleAlreadyAtAttemptsLimit(ex: AppointmentService.AlreadyAtAttemptsLimit)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Already at attempts limit exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }

  @ExceptionHandler
  fun handleExamIsNoLongerValidForModification(ex: ExamService.ExamIsNoLongerValidForModification)
          : ResponseEntity<BaseRestResponse<Nothing?>> {
    log.warn("Exam is no longer valid for modification exception thrown")
    return BaseRestResponse.badRequest(ex.message!!)
  }
}