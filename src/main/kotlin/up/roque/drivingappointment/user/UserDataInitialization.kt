package up.roque.drivingappointment.user

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import up.roque.drivingappointment.web.security.SecurityService

@Configuration
@Profile("dev")
class UserDataInitialization(private val securityService: SecurityService) : InitializingBean {
  private val log = LoggerFactory.getLogger(this.javaClass)

  override fun afterPropertiesSet() {
    initializeStudentData()
    initializeAdminData()
  }

  private fun initializeAdminData() {
    val adminUsernames = listOf("admin1", "admin2")
    for (adminUsername in adminUsernames) createNewAdmin(adminUsername)
  }

  private fun createNewAdmin(username: String) {
    val admin = securityService.createAdmin(username, "admin")
    log.info("Added new Admin: $admin")
  }

  private fun initializeStudentData() {
    val studentUsernames = listOf("student1", "student2", "student3")
    for (studentUsername in studentUsernames) createNewStudent(studentUsername)
  }

  private fun createNewStudent(username: String) {
    val student = securityService.createStudent(username, "password")
    log.info("Added new Student: $student")
  }
}