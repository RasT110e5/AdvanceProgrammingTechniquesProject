package up.roque.drivingappointment.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import up.roque.drivingappointment.security.AdminAuthorized
import up.roque.drivingappointment.security.SecurityService
import up.roque.drivingappointment.user.student.Student
import up.roque.drivingappointment.web.BaseRestResponse

@RestController
@RequestMapping("/api/users")
@AdminAuthorized
class UserController(private val securityService: SecurityService) {

  @GetMapping
  fun getAllUsers(): ResponseEntity<BaseRestResponse<List<BaseUser>>> {
    return BaseRestResponse.ok(securityService.getAllUsers())
  }

  @GetMapping("/students")
  fun getAllStudents(): ResponseEntity<BaseRestResponse<List<Student>>> {
    return BaseRestResponse.ok(securityService.getAllStudents())
  }
}