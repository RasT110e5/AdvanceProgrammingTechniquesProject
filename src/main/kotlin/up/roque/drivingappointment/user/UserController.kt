package up.roque.drivingappointment.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import up.roque.drivingappointment.security.AdminAuthorized
import up.roque.drivingappointment.security.SecurityService
import up.roque.drivingappointment.user.student.Student

@RestController
@RequestMapping("/api/users")
@AdminAuthorized
class UserController(private val securityService: SecurityService) {

  @GetMapping
  fun getAllUsers(): ResponseEntity<List<BaseUser>> {
    return ResponseEntity.ok(securityService.getAllUsers())
  }

  @GetMapping
  @RequestMapping("/students")
  fun getAllStudents(): ResponseEntity<List<Student>> {
    return ResponseEntity.ok(securityService.getAllStudents())
  }
}