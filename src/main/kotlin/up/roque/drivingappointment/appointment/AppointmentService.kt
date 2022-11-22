package up.roque.drivingappointment.appointment

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointmentRepository
import up.roque.drivingappointment.appointment.dto.AvailableAppointment
import up.roque.drivingappointment.appointment.eye.EyeAppointment
import up.roque.drivingappointment.appointment.eye.EyeAppointmentRepository
import up.roque.drivingappointment.security.SecurityService
import up.roque.drivingappointment.security.StudentAuthorized
import java.lang.RuntimeException
import java.time.LocalDateTime
import kotlin.random.Random.Default.nextInt

@Service
@Transactional(readOnly = true)
class AppointmentService(
  private val appointmentRepository: AppointmentRepository,
  private val drivingTestAppointmentRepository: DrivingTestAppointmentRepository,
  private val eyeAppointmentRepository: EyeAppointmentRepository,
  private val securityService: SecurityService,
) {
  private val log = LoggerFactory.getLogger(this.javaClass)

  @Transactional
  fun newDrivingTestAppointment(time: LocalDateTime): DrivingTestAppointment {
    log.info("Creating new driving test appointment at $time")
    val appointment = DrivingTestAppointment()
    appointment.time = time
    return drivingTestAppointmentRepository.save(appointment)
  }

  @Transactional
  fun newEyeAppointment(time: LocalDateTime): EyeAppointment {
    log.info("Creating new eye appointment at $time")
    val appointment = EyeAppointment()
    appointment.time = time
    appointment.doctorName = randomDoctorName()
    return eyeAppointmentRepository.save(appointment)
  }

  private fun randomDoctorName(): String {
    return arrayOf("Carl", "Claudia", "Jorge", "Anna")[nextInt(0, 4)]
  }

  fun findAllAvailableForReserveAppointments(): List<AvailableAppointment> {
    return drivingTestAppointmentRepository.findAllAvailableAfter(LocalDateTime.now())
  }

  @Transactional
  @StudentAuthorized
  fun reserveAppointment(id: Int, username: String): DrivingTestAppointment {
    val student = securityService.getStudent(username)
    val appointment = drivingTestAppointmentRepository.findById(id).orElseThrow { NoAppointmentFoundForId(id) }
    if (!appointment.isAvailable()) throw AppointmentAlreadyReserved()
    appointment.reserve(student)
    return drivingTestAppointmentRepository.save(appointment)
  }

  @Transactional
  @StudentAuthorized
  fun freeAppointment(id: Int, username: String) {
    val student = securityService.getStudent(username)
    val reservedAppointment = drivingTestAppointmentRepository.findReservedAppointmentFor(student)
      .orElseThrow { NoReservedAppointmentForId(id, username) }
    reservedAppointment.free()
    drivingTestAppointmentRepository.save(reservedAppointment)
  }

  class NoReservedAppointmentForId(id: Int, username: String) :
    RuntimeException("No appointment is reserved with id:$id and student:$username")

  class AppointmentAlreadyReserved : RuntimeException("Appointment is already reserved")
  class NoAppointmentFoundForId(id: Int) : RuntimeException("No appointment found for id:$id")
}