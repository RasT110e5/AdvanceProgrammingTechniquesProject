package up.roque.drivingappointment.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointmentRepository
import java.time.LocalDateTime

@DataJpaTest
class DrivingTestAppointmentRepositoryTests {
  @Autowired
  lateinit var repository: DrivingTestAppointmentRepository

  @Test
  @DisplayName("should return only available appointments")
  fun drivingTestAppointmentRepositoryTests_() {
    saveNewAppointment(false)
    saveNewAppointment(true)
    assertThat(repository.findAllAvailableAfterAsDto(LocalDateTime.now().minusMinutes(1))).isNotEmpty.hasSize(1)
  }

  private fun saveNewAppointment(available: Boolean) {
    val appointment = DrivingTestAppointment()
    appointment.time = LocalDateTime.now()
    appointment.available = available
    repository.save(appointment)
  }
}