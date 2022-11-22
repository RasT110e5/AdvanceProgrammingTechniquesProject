package up.roque.drivingappointment.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import up.roque.drivingappointment.user.BaseUser
import up.roque.drivingappointment.user.admin.Admin
import up.roque.drivingappointment.user.admin.AdminRepository
import up.roque.drivingappointment.user.student.Student
import up.roque.drivingappointment.user.student.StudentRepository
import java.util.*
import java.util.function.Supplier
import kotlin.collections.ArrayList

const val STUDENT = "STUDENT"
const val ADMIN = "ADMIN"

@Service
@Transactional(readOnly = true)
class SecurityService(
  private val studentRepository: StudentRepository,
  private val adminRepository: AdminRepository,
  private val encoder: PasswordEncoder,
) {
  private val log = LoggerFactory.getLogger(this.javaClass)

  @Transactional
  fun createStudent(username: String, password: String): Student {
    log.info("Creating new student with $username as username")
    return studentRepository.save(Student.with(username, encoder.encode(password)))
  }

  @Transactional
  fun createAdmin(username: String, password: String): Admin {
    log.info("Creating new Admin with $username as username")
    return adminRepository.save(Admin.with(username, encoder.encode(password)))
  }

  fun getAllUsers(): List<BaseUser> {
    val users = ArrayList<BaseUser>()
    users.addAll(studentRepository.findAll())
    users.addAll(adminRepository.findAll())
    return users
  }

  fun getAllStudents(): List<Student> {
    return studentRepository.findAll()
  }

  fun findStudent(username: String): Optional<Student> {
    log.info("Finding student with username: $username")
    return studentRepository.findById(username)
  }

  fun getStudent(username: String): Student {
    log.info("Fetching student with username: $username")
    return findStudent(username).orElseThrow { UsernameNotFoundException("For username: $username") }
  }

  fun findAdmin(username: String): Optional<Admin> {
    log.info("Finding admin with username: $username")
    return adminRepository.findById(username);
  }

}
