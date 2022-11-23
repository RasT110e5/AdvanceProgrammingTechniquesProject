package up.roque.drivingappointment.appointment

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointmentRepository
import up.roque.drivingappointment.appointment.drivingtest.dto.AvailableAppointment
import up.roque.drivingappointment.appointment.eye.EyeAppointment
import up.roque.drivingappointment.appointment.eye.EyeAppointmentRepository
import up.roque.drivingappointment.web.security.SecurityService
import up.roque.drivingappointment.web.security.StudentAuthorized
import java.lang.RuntimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random.Default.nextInt

@Service
@Transactional(readOnly = true)
class AppointmentService(
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
    return drivingTestAppointmentRepository.findAllAvailableAfterAsDto(LocalDateTime.now().minusHours(1))
  }

  @Transactional
  @StudentAuthorized
  fun reserveAppointment(id: Int, username: String): DrivingTestAppointment {
    val student = securityService.getStudent(username)
    if (student.attempts >= 3) throw AlreadyAtAttemptsLimit(username)
    val appointment = drivingTestAppointmentRepository.findById(id).orElseThrow { NoAppointmentFoundForId(id) }
    if (!appointment.isAvailable()) throw AppointmentAlreadyReserved()
    appointment.reserve(student)
    return drivingTestAppointmentRepository.save(appointment)
  }

  @Transactional
  @StudentAuthorized
  fun freeAppointment(id: Int, username: String) {
    val student = securityService.getStudent(username)
    val reservedAppointment = drivingTestAppointmentRepository.findReservedAppointmentFor(student, id)
      .orElseThrow { NoReservedAppointmentForId(id, username) }
    reservedAppointment.free()
    drivingTestAppointmentRepository.save(reservedAppointment)
  }

  @StudentAuthorized
  fun findAllReservedAppointmentsFor(username: String): MutableList<Appointment> {
    val student = securityService.getStudent(username)
    val reservedDrivingAppointments = drivingTestAppointmentRepository.findAllReservedAppointmentsFor(student)
    val reservedEyeAppointments = eyeAppointmentRepository.findAllReservedAppointmentsFor(student)
    val reservedAppointments: MutableList<Appointment> = reservedDrivingAppointments.toMutableList()
    reservedAppointments.addAll(reservedEyeAppointments)
    return reservedAppointments
  }

  fun getAppointmentBySecret(key: UUID): DrivingTestAppointment {
    return drivingTestAppointmentRepository.findBySecretKey(key).orElseThrow { NoAppointmentFoundForKey(key) }
  }

  fun getValidAppointmentBySecretAndUsername(key: UUID, username: String): DrivingTestAppointment {
    val appointment = getAppointmentBySecret(key)
    if (!appointment.isValidNow()) throw AppointmentIsNotValidForExamNow(appointment)
    if (!appointment.isAssignedTo(securityService.getStudent(username))) throw AppointmentNotReservedByUser(
      key,
      username
    )
    return appointment
  }


  @Transactional
  @StudentAuthorized
  fun reserveRandomEyeAppointment(username: String): EyeAppointment {
    val student = securityService.getStudent(username)
    val existentFutureAppointment =
      eyeAppointmentRepository.findFirstByStudentAndTimeIsAfter(student, LocalDateTime.now())
    return if (existentFutureAppointment.isPresent)
      existentFutureAppointment.get()
    else {
      val randomEyeAppointment = getRandomAvailableEyeAppointment()
      randomEyeAppointment.reserve(student)
      eyeAppointmentRepository.save(randomEyeAppointment)
    }
  }

  private fun getRandomAvailableEyeAppointment(): EyeAppointment {
    val eyeAppointment = eyeAppointmentRepository.findAllAvailableAfter(LocalDateTime.now())
    if (eyeAppointment.isEmpty()) throw NoEyeAppointmentIsAvailable()
    return eyeAppointment.random()
  }

  @Transactional
  fun reportStudentAttendance(appointment: DrivingTestAppointment) {
    if (appointment.student != null) {
      appointment.studentAbsent = false
      securityService.incrementStudentAttempts(appointment.student!!)
      drivingTestAppointmentRepository.save(appointment)
    }
  }

  @Transactional
  @StudentAuthorized
  fun reserveDrivingTestAppointment(username: String) {
    val randomDrivingTestAppointment = getRandomAvailableDrivingTestAppointment()
    randomDrivingTestAppointment.id?.let { reserveAppointment(it, username) }
  }

  private fun getRandomAvailableDrivingTestAppointment(): DrivingTestAppointment {
    val drivingTestAppointment = drivingTestAppointmentRepository.findAllAvailableAfter(LocalDateTime.now())
    if (drivingTestAppointment.isEmpty()) throw NoDrivingTestAppointmentIsAvailable()
    return drivingTestAppointment.random()
  }

  fun findAllPastAppointmentsForDate(date: LocalDate): MutableList<DrivingTestAppointment> {
    val firstTimeOfDate = date.atTime(0, 0)
    val lastTimeOfDate = date.atTime(23, 59, 59)
    return drivingTestAppointmentRepository.findAllByTimeBetweenAndBefore(
      firstTimeOfDate,
      lastTimeOfDate,
      LocalDateTime.now()
    )
  }


  class NoReservedAppointmentForId(id: Int, username: String) :
    RuntimeException("No appointment is reserved with id:$id and student:$username")

  class AppointmentIsNotValidForExamNow(appointment: DrivingTestAppointment) :
    RuntimeException("Appointment '$appointment' is not valid for exam now, only 1 hour from start time.")

  class AppointmentNotReservedByUser(key: UUID, username: String) :
    RuntimeException("Appointment with key:$key is not reserved by $username")

  class AlreadyAtAttemptsLimit(username: String) : RuntimeException("Student '$username' has already tried 3 times")
  class AppointmentAlreadyReserved : RuntimeException("Appointment is already reserved")
  class NoAppointmentFoundForId(id: Int) : RuntimeException("No appointment found for id:$id")
  class NoAppointmentFoundForKey(key: UUID) : RuntimeException("No appointment found for key:$key")
  class NoEyeAppointmentIsAvailable : RuntimeException("No eye appointment available")
  class NoDrivingTestAppointmentIsAvailable : RuntimeException("No driving test appointment available to retake exam")
}