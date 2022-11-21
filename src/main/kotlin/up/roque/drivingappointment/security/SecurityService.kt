package up.roque.drivingappointment.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import up.roque.drivingappointment.users.BaseUser
import up.roque.drivingappointment.users.admin.Admin
import up.roque.drivingappointment.users.admin.AdminRepository
import up.roque.drivingappointment.users.student.Student
import up.roque.drivingappointment.users.student.StudentRepository
import java.util.*
import kotlin.collections.ArrayList

const val STUDENT = "STUDENT"
const val ADMIN = "ADMIN"

@Service
@Transactional(readOnly = true)
class SecurityService(
  private val studentRepository: StudentRepository,
  private val adminRepository: AdminRepository,
  private val encoder: PasswordEncoder,
) : UserDetailsService {
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

  fun findStudent(username: String): Optional<Student> {
    log.info("Finding student with username: $username")
    return studentRepository.findById(username)
  }

  fun findAdmin(username: String): Optional<Admin> {
    log.info("Finding admin with username: $username")
    return adminRepository.findById(username);
  }

  override fun loadUserByUsername(username: String?): UserDetails {
    log.info("Authenticating username:$username")
    if (username == null) throw UsernameNotFoundException("The username cannot be null")
    return attemptLoad(username)
  }

  private fun attemptLoad(username: String): UserDetails {
    var user: User? = null
    findStudent(username)
      .ifPresent { user = newUserWith(it, SimpleGrantedAuthority(STUDENT)) }
    findAdmin(username)
      .ifPresent { user = newUserWith(it, SimpleGrantedAuthority(ADMIN)) }
    log.info("Authentication ended with: $user")
    return user ?: throw UsernameNotFoundException("User with username:$username could not be found")
  }

  fun newUserWith(baseUser: BaseUser, authority: SimpleGrantedAuthority): User {
    return User(baseUser.username, baseUser.secret, listOf(authority))
  }

  fun getAllStudents(): List<Student>? {
    return studentRepository.findAll();
  }
}
